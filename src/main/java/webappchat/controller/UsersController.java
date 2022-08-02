package webappchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import webappchat.data.UserRepository;
import webappchat.data.UsersStorage;
import webappchat.domain.UserData;

import java.util.HashSet;
import java.util.Set;


@RestController
@CrossOrigin
public class UsersController {

    UserRepository userRepo;

    @Autowired
    public UsersController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping("/registration/{userName}")
    public  ResponseEntity<UserData> register(@PathVariable String userName) {
        System.out.println("handling register user request: " + userName);

            UserData user = userRepo.findByUsername(userName);
            if (user == null) {
                user = new UserData();
                user.setUsername(userName);
                userRepo.save(user);
            }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        Set<String> users = new HashSet<>();
//        Iterable<UserData> users = userRepo.findAll();
        userRepo.findAll().forEach(u-> users.add(u.getUsername()));
        return users;
    }

//    @GetMapping("/messages/{userName}")
//    public Set<String> fetchAll(@PathVariable String userName) {
//        Set<String> users = new HashSet<>();
////        Iterable<UserData> users = userRepo.findAll();
//        userRepo.findAll().forEach(u-> users.add(u.getUsername()));
//        return users;
//    }
}
