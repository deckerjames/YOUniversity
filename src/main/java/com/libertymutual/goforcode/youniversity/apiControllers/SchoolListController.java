package com.libertymutual.goforcode.youniversity.apiControllers;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.youniversity.models.School;
import com.libertymutual.goforcode.youniversity.models.SchoolList;
import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.repositories.SchoolListRepository;
import com.libertymutual.goforcode.youniversity.repositories.SchoolRepository;
import com.libertymutual.goforcode.youniversity.repositories.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/list")
public class SchoolListController {

    private SchoolListRepository schoolListRepo;
    private SchoolRepository schoolRepo;
    private UserRepository userRepo;

    public SchoolListController(SchoolListRepository schoolListRepo, UserRepository userRepo, SchoolRepository schoolRepo) {
        this.schoolListRepo = schoolListRepo;
        this.userRepo = userRepo;
        this.schoolRepo = schoolRepo;

    }

    @ApiOperation("Returns a user's list(s)")
    @GetMapping("")
    public List<SchoolList> getList(Authentication auth) {
        User user = (User) auth.getPrincipal();
        user = userRepo.findOne(user.getId());
        return schoolListRepo.findAllByUser(user);
    }

    @ApiOperation("Deletes a list")
    @ApiParam(value = "schoolListId", required = true)
    @DeleteMapping("{id}")
    public SchoolList deleteList(@PathVariable long id) {
        SchoolList schoolList = schoolListRepo.findOne(id);
        schoolListRepo.delete(id);
        return schoolList;
    }

    //deprecated...not needed because the client won't implement
    //more than one list
//    @ApiOperation(value = "Creates a list")
//    @ApiParam(value = "SchoolList object", required = true)
//    @PostMapping("create")
//    public SchoolList createList(@RequestBody SchoolList schoolList, Authentication auth) {
//        User user = (User) auth.getPrincipal();
//        user = userRepo.findOne(user.getId());
//        schoolList.setUser(user);
//        return schoolListRepo.save(schoolList);
//    }
//    
//    //deprecated...not needed because the client won't change list name
//    //and won't have more than one list
//    @ApiOperation(value = "Changes the name of the list")
//    @ApiParam(value = "SchoolList object, schoolListId", required = true)
//    @PutMapping("{id}")
//    public SchoolList updateList(@RequestBody SchoolList schoolList, @PathVariable long id) {
//        schoolList.setId(id);
//        return schoolListRepo.save(schoolList);
//    }

    @ApiOperation(value = "Adds a school to a list")
    @ApiParam(value = "schoolListId, School object", required = true)
    @PostMapping("{listId}/add")
    public SchoolList addSchoolToList(@PathVariable long listId, @RequestBody School school) {
        SchoolList schoolList = schoolListRepo.findOne(listId);
        schoolRepo.save(school);
        schoolList.addSchool(school);
        schoolListRepo.save(schoolList);
        return schoolList;
    }

    @ApiOperation(value = "Deletes a school from a list")
    @ApiParam(value = "schoolId", required = true)
    @DeleteMapping("{listId}/delete/{schoolId}")
    public School deleteSchoolFromList(@PathVariable long schoolId, @PathVariable long listId) {
        try {
            School returnedSchool = null;
            SchoolList schoolList = schoolListRepo.findOne(listId);
            for (School school : schoolList.getSchools()) {
                if (school.getId() == schoolId) {
                    returnedSchool = school;
                    schoolList.getSchools().remove(school);
                    break;
                }
            }

            schoolListRepo.save(schoolList);
            schoolRepo.delete(schoolId);
            return returnedSchool;
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }
}
