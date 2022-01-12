package com.example.simpleHttpService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.simpleHttpService.dto.ResponseDto;

@Entity
@Table(name="employees")
public class Employee extends ResponseDto {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="firstname", nullable = false)
	private String firstName;
	
	@Column(name="type", nullable = false)
	private Integer type;
	
	@Column(name="salary", nullable = false)
	private Double salary;
	
	@Column(name="photoId", nullable = true)
	private String photoId;
	
	public Employee() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public String getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	
}
