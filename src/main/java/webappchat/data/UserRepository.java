package webappchat.data;

import org.springframework.data.repository.CrudRepository;
import webappchat.domain.UserData;

public interface UserRepository extends CrudRepository<UserData, Long> {
    UserData findByUsername(String username);
}
