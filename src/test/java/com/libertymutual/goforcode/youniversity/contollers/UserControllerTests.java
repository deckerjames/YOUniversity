package com.libertymutual.goforcode.youniversity.contollers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.Before;
import org.junit.Test;

import com.libertymutual.goforcode.youniversity.apiControllers.UserController;
import com.libertymutual.goforcode.youniversity.models.Preferences;
import com.libertymutual.goforcode.youniversity.models.School;
import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;
import com.libertymutual.goforcode.youniversity.services.RegistrationService;
import com.libertymutual.goforcode.youniversity.services.UpdateUserService;

public class UserControllerTests {

    private UserRepository userRepo;
    private UserController controller;
    private SchoolListRepository schoolListRepo;
    private Authentication auth;
    private RegistrationService registrationService;
    private UpdateUserService updateUserService;
    private PasswordEncoder encoder;

    @Before  
    public void setUp() {
        userRepo = mock(UserRepository.class);
        auth = mock(Authentication.class);
        schoolListRepo = mock(SchoolListRepository.class);
        encoder = mock(PasswordEncoder.class);
        registrationService = new RegistrationService(userRepo, encoder, schoolListRepo);;
        updateUserService = mock(UpdateUserService.class);
        controller = new UserController(userRepo, registrationService, updateUserService);
    }

    @Test
    public void test_getUser_returns_user_returned_by_the_repo() {
        // Arrange
        User fakeUser = new User();
        when(auth.getPrincipal()).thenReturn(fakeUser);
        fakeUser.setUsername("fakeUser");
        when(userRepo.findByUsername("fakeUser")).thenReturn(fakeUser);

        // Act
        User actual = controller.getUser(auth);

        // Assert
        assertThat(actual).isSameAs(fakeUser);
    }

    @Test
    public void test_updateUser_saves_updated_user() {
        // Arrange
        User loggedInUser = new User();
        loggedInUser.setId(2L);
        loggedInUser.setFirstName("dan");

        Preferences preferences = new Preferences();
        preferences.setLocation("WA");
        preferences.setMajor("someMajor");

        UserUpdateInfoDto changedUser = new UserUpdateInfoDto();
        changedUser.setFirstName("jonesy");
        changedUser.setLastName("smith");

        changedUser.setPreferences(preferences);
        
        when(updateUserService.updateUser(auth, changedUser)).thenReturn(loggedInUser);
        
        when(auth.getPrincipal()).thenReturn(loggedInUser);
        when(userRepo.findOne(2L)).thenReturn(loggedInUser); 
        
        
        when(userRepo.save(loggedInUser)).thenReturn(loggedInUser);

        when(updateUserService.updateUser(auth, changedUser)).thenReturn(loggedInUser);

        when(auth.getPrincipal()).thenReturn(loggedInUser);
        when(userRepo.findOne(2L)).thenReturn(loggedInUser);
        
        when(userRepo.save(loggedInUser)).thenReturn(loggedInUser);

        // Act
        User actual = controller.updateUser(auth, changedUser);

        // Assert
        assertThat(changedUser.getFirstName()).isEqualTo("jonesy");
        assertThat(changedUser.getLastName()).isEqualTo("smith");
        assertThat(changedUser.getPreferences().getLocation()).isEqualTo("WA");
        assertThat(changedUser.getPreferences().getMajor()).isEqualTo("someMajor");
    }
 
    @Test
    public void test_createUser_creates_new_user() {
        // Arrange
        User passedInUser = new User();
        passedInUser.setPassword("password");
        passedInUser.setId(7L);
        when(userRepo.save(passedInUser)).thenReturn(passedInUser);
        when(userRepo.findOne(7L)).thenReturn(passedInUser);
        when(registrationService.createUser(passedInUser)).thenReturn(passedInUser);

        SchoolList schoolList = new SchoolList();

        schoolList.setUser(passedInUser); 
        when(schoolListRepo.save(schoolList)).thenReturn(schoolList);

        passedInUser.setSchoolList(schoolList);

        when(userRepo.save(passedInUser)).thenReturn(passedInUser);

        // Act
        User createdUser = controller.createUser(passedInUser);

        // Assert
        assertThat(createdUser).isNotNull();
    }
}