package info.jjhop.gdcollector.workers

import spock.lang.Specification

import com.rabbitmq.client.Channel;
import org.springframework.dao.DataAccessException
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import info.jjhop.gdcollector.model.Data
import info.jjhop.gdcollector.repository.DataRepository

class InputDataWorkerSpecification extends Specification {

    def "InputDataWorker#onMessage should consume message when dataDAO works properly"() {
        given:
            def dataDao = Mock(DataRepository.class)
            def message = Mock(Message.class)
                message.getBody() >> '528.53;21.033333;d5252ccd-4312-473e-a974-65e707fa432f'
                message.getMessageProperties() >> { new MessageProperties() {{
                    setDeliveryTag(Long.MAX_VALUE)
                }}}
            def channel = Mock(Channel.class)

            InputDataWorker worker = new InputDataWorker(dataDao)

        when:
            worker.onMessage(message, channel)

        then:
            1 * dataDao.save(_ as Data)
            1 * channel.basicAck(Long.MAX_VALUE, false)
    }

    def "InputDataWorker#onMessage should not consume message and requeue it when dataDAO#save failed"() {
        given:
            def dataDao = Mock(DataRepository.class)
                dataDao.save(_ as Data) >> { throw new DataAccessException("!") {{}} }
            def message = Mock(Message.class)
            message.getBody() >> '528.43;21.033333;d5252ccd-4312-473e-a974-65e707fa432f'
            message.getMessageProperties() >> { new MessageProperties() {{
                setDeliveryTag(Long.MAX_VALUE)
            }}}
            def channel = Mock(Channel.class)
            InputDataWorker worker = new InputDataWorker(dataDao)
        when:
            worker.onMessage(message, channel)
        then:
            1 * channel.basicReject(Long.MAX_VALUE, true)
    }
}
