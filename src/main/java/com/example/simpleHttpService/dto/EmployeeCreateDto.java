package com.example.simpleHttpService.dto;

public class EmployeeCreateDto {
	private String firstName;
	private Integer type;
	
	public EmployeeCreateDto() {
		
	}
	
	public EmployeeCreateDto(String firstName, int type) {
		this.setFirstName(firstName);
		this.setType(type);
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
}
