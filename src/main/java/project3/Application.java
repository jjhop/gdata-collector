package project3;

import java.util.Date;
import java.util.UUID;
import static java.util.UUID.fromString;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ImportResource({"classpath:rabbit-config.xml"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // long;lat;uuid
    @PostMapping("/apply")
    public String applyInfo(@RequestBody String data) {

        // Tutaj wrzucamy do rabbita i dopiero tam parsujemy
        return "";
    }
}

class InputDataWorker implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }
}

class MessageParser {

    public Data parse(String input) {
        String[] data = input.split(";");
        return new Data(fromString(data[0]), data[1], data[2]);
    }

}

class Data {

    private UUID dataId;
    private Date createdAt;
    private UUID uuid;
    private String longitude;
    private String latitude;

    public Data(UUID uuid, String longitude, String latitude) {
        this.createdAt = new Date();
        this.uuid = uuid;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
