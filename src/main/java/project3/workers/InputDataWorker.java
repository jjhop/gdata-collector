package project3.workers;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project3.repository.DataRepository;
import project3.model.Data;

@Component
public class InputDataWorker implements ChannelAwareMessageListener {

    private final static Logger LOG = LoggerFactory.getLogger(InputDataWorker.class);

    private final DataRepository dataRepository;

    @Autowired
    public InputDataWorker(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageBody = new String(message.getBody());
        LOG.trace("Trying to consume message with deliveryTag={} and body=[{}]", deliveryTag, messageBody);
        Data geoData = Data.fromString(messageBody);
        LOG.trace("Message with deliveryTag={} successfully transformed to Data object [{}]", messageBody, geoData);
        try {
            dataRepository.save(geoData);
            LOG.trace("Message with deliveryTag={} successfully saved to repository.", messageBody);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
            LOG.warn("Cant's save message with deliveryTag={} to repository. Message was successfully requeued.", deliveryTag);
        }
    }
}
