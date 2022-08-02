package webappchat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import webappchat.data.UsersStorage;
import webappchat.domain.MessageModel;

@RestController
public class Controller {

    @Autowired
    private SimpMessagingTemplate  simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("Handling message: " + message + " to: " + to );
        boolean isExists = UsersStorage.getInstance().getUsers().contains(to);
        if(isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }

    }
}
