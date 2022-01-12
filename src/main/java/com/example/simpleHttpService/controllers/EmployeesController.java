package com.example.simpleHttpService.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpleHttpService.dto.EmployeeCreateDto;
import com.example.simpleHttpService.dto.MessageResponseDto;
import com.example.simpleHttpService.dto.ResponseDto;
import com.example.simpleHttpService.model.Employee;
import com.example.simpleHttpService.repository.IEmployeesRepository;

@RestController
@RequestMapping
public class EmployeesController {
	/* Salary list in Array to assign depending on type value */
	double SALARY_LIST[] = {1200, 1600, 2000};
	
	/* Employees Repository */
	@Autowired
	private IEmployeesRepository employeesRepository;
	
	/* Testing route */
	@GetMapping("/test")
	public String test() {
		return "Hola, mundo.";
	}
	
	/* (GET) Reading all employees and by id */
	@GetMapping("/employees")
	public ResponseEntity<Iterable<Employee>> findAll() {
		return new ResponseEntity<>(employeesRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Optional<Employee>> findById(@PathVariable(name="id") Long id) {
		return new ResponseEntity<>(employeesRepository.findById(id), HttpStatus.OK);
	}
	
	/* (POST) Create employee method */
	@PostMapping("/employees")
	public ResponseEntity<ResponseDto> create(@RequestBody EmployeeCreateDto empData) {
		/* validate request */
		if(empData.getFirstName() == null || empData.getType() == null) {
			return new ResponseEntity<>(new MessageResponseDto("Alguno de los campos está vacío o el JSON no es correcto."), HttpStatus.CONFLICT);
		}
		
		/* validate if type is between the expected values */
		if(empData.getType() < 1 || empData.getType() > SALARY_LIST.length) {
			return new ResponseEntity<>(new MessageResponseDto("Los tipos de empleados son: trabajador (1), encargado (2) y jefe (3). Usted escribió: " + empData.getType()), HttpStatus.CONFLICT);
		}
		
		/* if everything is correct, create employee entity */
		Employee emp = new Employee();
		
		emp.setFirstName(empData.getFirstName());
		emp.setType(empData.getType());
		emp.setSalary(SALARY_LIST[empData.getType() - 1]);
		
		/* save entity */
		return new ResponseEntity<>(employeesRepository.save(emp), HttpStatus.OK);
	}
	
	/* (PUT) Update employee method */
	@PutMapping("/employees/{id}")
	public ResponseEntity<ResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody Employee empData) {
		
		Optional<Employee> optEmp = employeesRepository.findById(id);
		
		if(optEmp == null) {
			return new ResponseEntity<>(new MessageResponseDto("No se encuentra el trabajador: " + id), HttpStatus.NOT_FOUND);
		} else {
			Employee emp = optEmp.get();
			/* validate request */
			if(empData.getFirstName() == null || empData.getType() == null || empData.getSalary() == null) { 
				return new ResponseEntity<>(new MessageResponseDto("Alguno de los campos está vacío o el JSON no es correcto."), HttpStatus.CONFLICT);
			}
			
			/* update values */
			emp.setFirstName(empData.getFirstName());
			emp.setType(empData.getType());
			emp.setSalary(empData.getSalary());
			
			/* save values */
			employeesRepository.save(emp);
			
			return new ResponseEntity<>(emp, HttpStatus.OK);
		}
	}
	
	/* (DELETE) Delete method */
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable(name = "id") Long id) {
		try {
			employeesRepository.deleteById(id);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<>(new MessageResponseDto("El id que busca no es correcto."), HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(new MessageResponseDto("Registro borrado correctamente"), HttpStatus.OK);
	}
	
	/* (GET) Find employees by type */
	@GetMapping("/employees/filter")
	public ResponseEntity<Iterable<Employee>> findByType(@RequestParam("type") Integer type) {
		return new ResponseEntity<>(employeesRepository.findByType(type), HttpStatus.OK);
	}
}
