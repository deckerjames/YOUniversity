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

    @Before
    public void setUp() {
        userRepo = mock(UserRepository.class);
        auth = mock(Authentication.class);
        schoolListRepo = mock(SchoolListRepository.class);
        registrationService = mock(RegistrationService.class);
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
        when(auth.getPrincipal()).thenReturn(loggedInUser);
        when(userRepo.findOne(2L)).thenReturn(loggedInUser);
        when(userRepo.save(loggedInUser)).thenReturn(loggedInUser);

        Preferences preferences = new Preferences();
        preferences.setLocation("WA");
        preferences.setMajor("someMajor");

        UserUpdateInfoDto changedUser = new UserUpdateInfoDto();
        changedUser.setFirstName("jonesy");
        changedUser.setLastName("smith");
        changedUser.setPreferences(preferences);

        // Act
        User updatedUser = controller.updateUser(auth, changedUser);

        // Assert
        assertThat(updatedUser.getFirstName()).isEqualTo("jonesy");
        assertThat(updatedUser.getLastName()).isEqualTo("smith");
        assertThat(updatedUser.getPreferences().getLocation()).isEqualTo("WA");
        assertThat(updatedUser.getPreferences().getMajor()).isEqualTo("someMajor");
        verify(auth).getPrincipal();
    }

    @Test
    public void test_createUser_creates_new_user() {
        // Arrange
        User passedInUser = new User();
        passedInUser.setPassword("password");
        passedInUser.setId(7L);
        when(userRepo.save(passedInUser)).thenReturn(passedInUser);
        when(userRepo.findOne(7L)).thenReturn(passedInUser);

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
