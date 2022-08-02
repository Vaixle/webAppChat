package webappchat.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @OneToMany
    private List<Message> messages;

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
