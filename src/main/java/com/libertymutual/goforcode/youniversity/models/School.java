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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity
@Table(name = "school")
public class School {

	public School() {
	}

	public School(Long schoolApiID, int rank, int size, String schoolName, String schoolLocation, String state, double admission,
			String highestDegree, String schoolUrl, int inState, int outState, int avgNet, String comment) {
		this.schoolApiId = schoolApiID;
		this.rank = rank;
		this.size = size;
		this.schoolName = schoolName;
		this.schoolLocation = schoolLocation;
		this.state = state;
		this.admission = admission;
		this.highestDegree = highestDegree;
		this.schoolUrl = schoolUrl;
		this.inState = inState;
		this.outState = outState;
		this.avgNet = avgNet;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long schoolApiId;

	@Column
	private int rank;
	
	@Column
	private int size;

	@Column
	private String schoolName;

	@Column
	private String schoolLocation;

	@Column
	private String state;

	@Column
	private double admission;

	@Column
	private String highestDegree;

	@Column
	private String schoolUrl;

	@Column
	private int inState;

	@Column
	private int outState;

	@Column
	private int avgNet;

	@Column
	private String comment;

	@ManyToMany(mappedBy = "schools", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.DETACH })
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

	public void setSchoolList(List<SchoolList> schoolList) {
		this.schoolList = schoolList;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolLocation() {
		return schoolLocation;
	}

	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getAdmission() {
		return admission;
	}

	public void setAdmission(double admission) {
		this.admission = admission;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getSchoolUrl() {
		return schoolUrl;
	}

	public void setSchoolUrl(String schoolUrl) {
		this.schoolUrl = schoolUrl;
	}

	public int getInState() {
		return inState;
	}

	public void setInState(int inState) {
		this.inState = inState;
	}

	public int getOutState() {
		return outState;
	}

	public void setOutState(int outState) {
		this.outState = outState;
	}

	public int getAvgNet() {
		return avgNet;
	}

	public void setAvgNet(int avgNet) {
		this.avgNet = avgNet;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
