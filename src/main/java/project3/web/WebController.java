package project3.web;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {

    private final static Logger LOG = LoggerFactory.getLogger(WebController.class);

    private static final Pattern reqPattern =
            Pattern.compile("(\\d+\\.\\d+;){2}[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public WebController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // long;lat;userId
    @PostMapping("/apply")
    public void applyInfo(@RequestBody String data, HttpServletResponse response) {
        LOG.trace("Getting request [{}]", data);
        try {
            if (isValidRequest(data)) {
                rabbitTemplate.convertAndSend(data);
                LOG.trace("Valid request [{}] data was sent to queue.", data);
                response.setStatus(SC_ACCEPTED);
            } else {
                LOG.warn("Invalid request [{}] was ignored.", data);
                response.setStatus(SC_BAD_REQUEST);
            }
        } catch (Exception ex) {
            LOG.error("Error while processing request [{}].", data, ex);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidRequest(String requestData) {
        return reqPattern.matcher(requestData).matches();
    }
}
