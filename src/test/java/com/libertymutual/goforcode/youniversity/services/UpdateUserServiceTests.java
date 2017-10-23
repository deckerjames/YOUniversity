package com.libertymutual.goforcode.youniversity.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import com.libertymutual.goforcode.youniversity.models.Preferences;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

public class UpdateUserServiceTests {

	private UserRepository userRepository;
	private Authentication auth;
	private UpdateUserService updateUserService;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		auth = mock(Authentication.class);

		updateUserService = new UpdateUserService(userRepository);
	}

	@Test
	public void test_updateUserService_saves_updated_user() {
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

		when(auth.getPrincipal()).thenReturn(loggedInUser);
		when(userRepository.findOne(2L)).thenReturn(loggedInUser);

		when(userRepository.save(loggedInUser)).thenReturn(loggedInUser);

		// Act
		User actual = updateUserService.updateUser(auth, changedUser);

		// Assert
		assertThat(actual.getFirstName()).isEqualTo("jonesy");
		assertThat(actual.getLastName()).isEqualTo("smith");
		assertThat(actual.getPreferences().getLocation()).isEqualTo("WA");
		assertThat(actual.getPreferences().getMajor()).isEqualTo("someMajor");
		verify(auth).getPrincipal();
	}

}
