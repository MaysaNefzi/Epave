package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Greeting;
import com.pfe.star.epave.Models.HelloMessage;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class NotifController {
    @MessageMapping("/97852456")
    @SendTo("/topic/96969696")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
