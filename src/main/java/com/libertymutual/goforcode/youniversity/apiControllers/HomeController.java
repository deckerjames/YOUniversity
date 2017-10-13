package com.libertymutual.goforcode.youniversity.apiControllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

@RestController
@RequestMapping("/")
public class HomeController {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public HomeController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

}
