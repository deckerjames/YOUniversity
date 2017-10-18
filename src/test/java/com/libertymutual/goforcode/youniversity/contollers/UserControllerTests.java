package com.libertymutual.goforcode.youniversity.contollers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.Before;
import org.junit.Test;

import com.libertymutual.goforcode.youniversity.apiControllers.UserController;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

public class UserControllerTests {

    private UserRepository userRepo;
    private UserController controller;
    private SchoolListRepository schoolListRepo;
    private PasswordEncoder encoder;
    private Authentication auth;

    @Before
    public void setUp() {
        userRepo = mock(UserRepository.class);
        auth = mock(Authentication.class);
        controller = new UserController(userRepo, encoder, schoolListRepo);
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
    public void test_updateUser_saves_updated_user_t_the_repo() {
        // Arrange
        User loggedInUser = new User();
        UserUpdateInfoDto passedInUser = new UserUpdateInfoDto();
        when(auth.getPrincipal()).thenReturn(loggedInUser);
        loggedInUser.setId(7l);
        
        // Act
        User updatedUser = controller.updateUser(auth, passedInUser);

        // Assert
        assertThat(updatedUser).isNotNull();
    }
}
