package ee.mihkel.backend0626.controller;

import ee.mihkel.backend0626.dto.Greeting;
import ee.mihkel.backend0626.dto.HelloMessage;
import ee.mihkel.backend0626.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final EmailService emailService;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("send-email")
    public String sendEmail() {
        emailService.sendSimpleMessage("vahermihkel@gmail.com", "Pealkiri", "Sisu");
        return "Email sent";
    }

}