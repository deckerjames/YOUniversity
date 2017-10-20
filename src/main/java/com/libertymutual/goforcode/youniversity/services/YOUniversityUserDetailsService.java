package com.libertymutual.goforcode.youniversity.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

@Service
public class YOUniversityUserDetailsService implements UserDetailsService {

    private UserRepository usersRepository;

    public YOUniversityUserDetailsService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
  
}
