package com.libertymutual.goforcode.youniversity.apiControllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;
import com.libertymutual.goforcode.youniversity.services.RegistrationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private RegistrationService registrationService;

    public UserController(UserRepository userRepository, RegistrationService registrationService) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @ApiOperation(value = "Returns user")
    @GetMapping("")
    public User getUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        return userRepository.findByUsername(username);
    }

    @ApiOperation(value = "Updates a user")
    @ApiParam(value = "User object", required = true)
    @PutMapping("")
    public User updateUser(Authentication auth, @RequestBody UserUpdateInfoDto user) {
        User loggedInUser = (User) auth.getPrincipal();

        User fromDb = userRepository.findOne(loggedInUser.getId());

        if (user.getFirstName() != null)
            fromDb.setFirstName(user.getFirstName());
        if (user.getLastName() != null)
            fromDb.setLastName(user.getLastName());
        if (user.getPreferences() != null)
            fromDb.setPreferences(user.getPreferences());

        return userRepository.save(fromDb);
    }

    @ApiOperation(value = "Creates a user")
    @ApiParam(value = "User object", required = true)
    @PostMapping("create")
    public User createUser(@RequestBody User user) {
        return registrationService.createUser(user);
    }
}
