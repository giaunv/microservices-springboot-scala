package user

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.repository.MongoRepository

import scala.annotation.meta.field

trait RegisteredUserRepository extends MongoRepository[RegisteredUser, String]

case class RegisteredUser (id: String, @(Indexed@field)(unique = true) emailAddress: String, password: String)