package com.libertymutual.goforcode.youniversity.apiControllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private SchoolListRepository schoolListRepo;

    public UserController(UserRepository userRepository, PasswordEncoder encoder, SchoolListRepository schoolListRepo) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.schoolListRepo = schoolListRepo;
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
        String password = user.getPassword();
        String encryptedPassword = encoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);  
        SchoolList schoolList = new SchoolList();
        schoolList.setName("Favorites");   
        user = userRepository.findOne(user.getId());
        schoolList.setUser(user);  
        schoolListRepo.save(schoolList);
        user.setSchoolList(schoolList);
        userRepository.save(user);
        return user;
    }
}
