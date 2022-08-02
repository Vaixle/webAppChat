package webappchat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import webappchat.data.UserRepository;
import webappchat.data.UsersStorage;
import webappchat.domain.Message;
import webappchat.domain.MessageModel;
import webappchat.domain.UserData;

@RestController
public class MessageController {


    private SimpMessagingTemplate  simpMessagingTemplate;

    private UserRepository userRepo;

    @Autowired
    public MessageController(UserRepository userRepo, SimpMessagingTemplate  simpMessagingTemplate) {
        this.userRepo = userRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("Handling message: " + message + " to: " + to );
        UserData user = userRepo.findByUsername(to);
        boolean isExists = user != null;
        userRepo.findByUsername(to);
        UserData fromUser = userRepo.findByUsername(message.getFromLogin());
        fromUser.addSentMessage(message);
        userRepo.save(fromUser);
        if(isExists) {
            user.addInboxMessage(message);
            userRepo.save(user);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }

    }
}
