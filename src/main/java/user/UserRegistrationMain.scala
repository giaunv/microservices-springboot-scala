package user

import org.springframework.boot.SpringApplication

object UserRegistrationMain {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[UserRegistrationConfiguration], args:_*)
}
