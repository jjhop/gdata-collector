package project3.web

import javax.servlet.http.HttpServletResponse

import spock.lang.Specification

import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate

class WebControllerSpecification extends Specification {

    def "WebController#applyInfo should send input to queue and set SC_ACCEPTED for valid input" () {
        given:
            def rabbitTemplate = Mock(RabbitTemplate.class)
            def validInput = '52.216667;21.033333;d5252ccd-4312-473e-a974-65e707fa432f'
            def response = Mock(HttpServletResponse.class)
            WebController controller = new WebController(rabbitTemplate)
        when:
            controller.applyInfo(validInput, response)
        then:
            1 * rabbitTemplate.convertAndSend(_ as String)
            1 * response.setStatus(HttpServletResponse.SC_ACCEPTED)
    }


    def "WebController#apply should not send input to queue and set SC_BAD_REQUEST for invalid input" () {
        given:
            def rabbitTemplate = Mock(RabbitTemplate.class)
            def invalidInput = '528.;21.033333;d5252ccd-4312-473e-a974-65e707fa432f'
            def response = Mock(HttpServletResponse.class)
            WebController controller = new WebController(rabbitTemplate)
        when:
            controller.applyInfo(invalidInput, response)
        then:
            0 * rabbitTemplate.convertAndSend(_ as String)
            1 * response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
    }

    def "WebController#apply should send SC_INTERNAL_SERVER_ERROR when error occurs"() {
        given:
            def rabbitTemplate = Stub(RabbitTemplate.class)
                rabbitTemplate.convertAndSend(_) >> { throw new AmqpException("Error!") }
            def validInput = '52.216667;21.033333;d5252ccd-4312-473e-a974-65e707fa432f'
            def response = Mock(HttpServletResponse.class)
            WebController controller = new WebController(rabbitTemplate)
        when:
            controller.applyInfo(validInput, response)
        then:
            1 * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
    }
}
