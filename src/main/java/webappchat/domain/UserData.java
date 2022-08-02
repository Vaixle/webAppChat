package webappchat.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Message> inboxMessages;

    @OneToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> sentMessages;

    public void addInboxMessage(Message message) {
        this.inboxMessages.add(message);
    }

    public void addSentMessage(Message message) {
        this.sentMessages.add(message);
    }
}
