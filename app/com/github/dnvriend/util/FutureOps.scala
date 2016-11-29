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

package com.github.dnvriend.util

import play.api.mvc.{Result, Results}

import scala.concurrent.{ExecutionContext, Future}

object FutureOps extends Results {

  implicit class FutureImplicits(val self: Future[_]) extends AnyVal {
    def mapOk(message: String)(implicit ec: ExecutionContext): Future[Result] =
      self.map(_ => Ok(message))
  }

  implicit class ResultFutureImplicits(val self: Future[Result]) extends AnyVal {
    def recoverInternalServerError(implicit ec: ExecutionContext): Future[Result] =
      self.recover {
        case e: Throwable =>
          InternalServerError(e.getMessage)
      }
  }
}
