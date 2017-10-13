package com.libertymutual.goforcode.youniversity.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class School {

    public School() {
    }

    public School(Long schoolApiID) {
        this.schoolApiId = schoolApiID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long schoolApiId;

    @ManyToMany(mappedBy = "schools", cascade = CascadeType.ALL)
    private List<SchoolList> schoolList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolApiId() {
        return schoolApiId;
    }

    public void setSchoolApiId(Long schoolApiId) {
        this.schoolApiId = schoolApiId;
    }

    // public List<SchoolList> getSchoolList() {
    // return schoolList;
    // }

    public void setSchoolList(List<SchoolList> schoolList) {
        this.schoolList = schoolList;
    }

}
