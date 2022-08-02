package webappchat.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    private String fromLogin;

    private String subject;

    private Date createdAt;

    @PrePersist
    void placedAt() {
        this.createdAt = new Date();
    }
}
