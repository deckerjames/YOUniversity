package com.libertymutual.goforcode.youniversity.contollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;


import com.libertymutual.goforcode.youniversity.apiControllers.SchoolListController;
import com.libertymutual.goforcode.youniversity.models.School;
import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.SchoolRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

public class SchoolListControllerTests {

	private Authentication auth;
	private SchoolListRepository schoolListRepo;
	private UserRepository userRepo;
	private SchoolRepository schoolRepo;
	private SchoolListController controller;
	
	
	@Before
	public void setup() {
		auth = mock(Authentication.class);
		schoolListRepo = mock(SchoolListRepository.class);
		userRepo = mock(UserRepository.class);
		schoolRepo = mock(SchoolRepository.class);
		controller = new SchoolListController(schoolListRepo, userRepo, schoolRepo);
	}
	
		@Test
		public void get_a_list_for_one_user() {
			//arrange
			User fakeGuy = new User();
			ArrayList<SchoolList> fakeList = new ArrayList<SchoolList>();
			when(auth.getPrincipal()).thenReturn((fakeGuy));
			when(userRepo.findOne(4L)).thenReturn(fakeGuy);
			when(schoolListRepo.findAllByUser(fakeGuy)).thenReturn(fakeList);
			
			//act
			List<SchoolList> returnedList = controller.getList(auth);
			
			//assert
			assertThat(returnedList).isEqualTo(fakeList);
	
		}

		
		@Test
		public void delete_a_list_for_the_user() {
			//arrange
			SchoolList new1 = new SchoolList();
			when(schoolListRepo.findOne(4L)).thenReturn(new1);	
			
			//act
			SchoolList deletedList = controller.deleteList(4L);
			
			//assert
			assertThat(deletedList).isSameAs(new1);
			verify(schoolListRepo).findOne(4L);
			verify(schoolListRepo).delete(4L);
			
		}
		
		@Test
		public void add_a_school_to_a_list() {
			//arrange
			SchoolList new1 = new SchoolList();
			when(schoolListRepo.findOne(4L)).thenReturn(new1);
			
			School aSchool = new School();
			
			//act
			SchoolList addList = controller.addSchoolToList(4L, aSchool);
			
			//assert
			verify(schoolListRepo).save(addList);
			verify(schoolListRepo).findOne(4L);
		}
		
		
		
		@Test
		public void delete_a_school_from_a_list() {
			//arrange
			SchoolList new1 = new SchoolList();
			when(schoolListRepo.findOne(4L)).thenReturn(new1);
			
			School aSchool = new School();
			when(schoolRepo.findOne(2L)).thenReturn(aSchool);
			
			new1.addSchool(aSchool);
			aSchool.setId(2L);
			
			//act
			School actualSchool = controller.deleteSchoolFromList(2L, 4L);
			
			//assert
			verify(schoolListRepo).save(new1);
			verify(schoolRepo).delete(2L);
			assertThat(actualSchool).isEqualTo(aSchool);
		}
		
		@Test
		public void receive_an_exception_when_attempting_to_delete_a_school_that_doesnt_exist() {
			
			//arrange
			SchoolList new1 = new SchoolList();
			when(schoolListRepo.findOne(4L)).thenReturn(new1);
			when(schoolRepo.findOne(1L)).thenThrow(new EmptyResultDataAccessException(0));
			
			School aSchool = new School();
			when(schoolRepo.findOne(2L)).thenReturn(aSchool);
			
			new1.addSchool(aSchool);
			aSchool.setId(2L);
			
			//act
			School actualSchool = controller.deleteSchoolFromList(1L, 4L);
			
			//assert
			assertThat(actualSchool).isNull();
			verify(schoolListRepo).findOne(4L);
		}
}
