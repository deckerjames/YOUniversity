package com.libertymutual.goforcode.youniversity.services;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;
import com.libertymutual.goforcode.youniversity.services.RegistrationService;

public class RegistrationServiceTests {

    private UserRepository userRepo;
    private SchoolListRepository schoolListRepo;
    private RegistrationService registrationService;
    private PasswordEncoder encoder;

    @Before
    public void setUp() {
        userRepo = mock(UserRepository.class);
        schoolListRepo = mock(SchoolListRepository.class);
        encoder = mock(PasswordEncoder.class);
        registrationService = new RegistrationService(userRepo, encoder, schoolListRepo);
    }

    @Test
    public void test_createUser_creates_new_user() {
        // Arrange
        User passedInUser = new User();
        passedInUser.setId(7L);
        when(userRepo.findOne(7L)).thenReturn(passedInUser);
        when(userRepo.save(passedInUser)).thenReturn(passedInUser);
        
        SchoolList schoolList = new SchoolList();

        schoolList.setUser(passedInUser);
        when(schoolListRepo.save(schoolList)).thenReturn(schoolList);

        passedInUser.setSchoolList(schoolList);

        when(userRepo.save(passedInUser)).thenReturn(passedInUser);

        // Act
        User createdUser = registrationService.createUser(passedInUser);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser).isSameAs(passedInUser);
    }
}
