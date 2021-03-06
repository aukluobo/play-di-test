/*
 * Copyright 2016 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dnvriend.component.controller

import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}
import scalaz._

abstract class ValidatingController extends Controller {
  def handleAndRespond[A: Writes](disjunction: Future[Disjunction[String, A]])(implicit ec: ExecutionContext) =
    Action.async(
      disjunction.map {
        case DRight(value)  => Ok(Json.toJson(value))
        case DLeft(message) => BadRequest(message)
      }
    )
}
