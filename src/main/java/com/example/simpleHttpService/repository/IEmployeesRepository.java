package com.example.simpleHttpService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.simpleHttpService.model.Employee;

@Repository
public interface IEmployeesRepository extends CrudRepository<Employee, Long> {
	public Iterable<Employee> findByType(Integer type);
}
