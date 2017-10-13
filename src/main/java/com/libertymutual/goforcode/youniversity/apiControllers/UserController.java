package com.libertymutual.goforcode.youniversity.apiControllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("")
    public User getUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        return userRepository.findByUsername(username);
    }

    @PutMapping("")
    public User updateUser(User user) {
        String username = user.getUsername();
        userRepository.findByUsername(username);
        return userRepository.save(user);
    }

    @PostMapping("create")
    public User createUser(@RequestBody User user) {
        String password = user.getPassword();
        System.out.println("PW is: " + encoder.encode(password));
        String encryptedPassword = encoder.encode(password);
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

}
