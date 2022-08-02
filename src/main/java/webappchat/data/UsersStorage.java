package webappchat.data;

import java.util.HashSet;
import java.util.Set;

public class UsersStorage {

    private static UsersStorage instance;
    private Set<String> users;

    private UsersStorage(){
        users = new HashSet<>();
    }

    public static synchronized UsersStorage getInstance() {
        if (instance == null) {
            instance = new UsersStorage();
        }
        return instance;
    }

    public Set<String> getUsers(){
        return users;
    }

    public void setUser(String username) throws Exception {
        if(users.contains(username)) {
            throw new Exception("User already exists with login: " + username);
        }
        users.add(username);
    }

}
