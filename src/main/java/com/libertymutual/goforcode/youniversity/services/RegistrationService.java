package com.libertymutual.goforcode.youniversity.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

@Service
public class RegistrationService implements CreateUserInterface {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private SchoolListRepository schoolListRepo;

    public RegistrationService(UserRepository userRepository, PasswordEncoder encoder, SchoolListRepository schoolListRepo) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.schoolListRepo = schoolListRepo; 
    }

    @Override
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
