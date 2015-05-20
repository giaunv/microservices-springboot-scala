package user

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

@RestController
class UserRegistrationController @Autowired() (private val registeredUserRepository: RegisteredUserRepository, rabbitTemplate: RabbitTemplate){

  import MessagingNames._

  @RequestMapping(value = Array("/user"), method = Array(RequestMethod.POST))
  def registerUser(@RequestBody request: RegistrationRequest) = {
    val registeredUser = new RegisteredUser(null, request.emailAddress, request.password)
    registeredUserRepository.save(registeredUser)

    rabbitTemplate.convertAndSend(exchangeName, routingKey, NewRegistrationNotification(registeredUser.id, request.emailAddress, request.password))

    RegistrationResponse(registeredUser.id, request.emailAddress)
  }
}

case class RegistrationRequest(emailAddress: String, password: String)

case class RegistrationResponse(id: String, emailAddress: String)

case class NewRegistrationNotification(id: String, emailAddress: String, password: String)

object MessagingNames {
  val queueName = "user-registration"
  val routingKey = queueName
  val exchangeName = "user-registrations"
}