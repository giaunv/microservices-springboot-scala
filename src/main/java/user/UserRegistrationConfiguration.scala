package user

import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, Primary}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class UserRegistrationConfiguration {
  @Bean
  @Primary
  def scalaObjectMaper() = new ScalaObjectMapper

  @Bean
  def rabbitTemplate(connectionFactory: ConnectionFactory) = {
    val template = new RabbitTemplate(connectionFactory)

    val jsonConverter = new Jackson2JsonMessageConverter
    jsonConverter.setJsonObjectMapper(scalaObjectMaper())

    template.setMessageConverter(jsonConverter)

    template
  }

  @Bean
  def userRegistrationsExchange() = new TopicExchange(MessagingNames.exchangeName)
}
