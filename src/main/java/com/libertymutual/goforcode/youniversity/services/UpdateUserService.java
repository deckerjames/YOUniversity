package com.libertymutual.goforcode.youniversity.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

@Service
public class UpdateUserService implements UpdateUserInterface {

    private UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

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

}
