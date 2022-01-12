package com.example.simpleHttpService.controllers;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simpleHttpService.dto.MessageResponseDto;
import com.example.simpleHttpService.dto.ResponseDto;
import com.example.simpleHttpService.model.DataBaseFile;
import com.example.simpleHttpService.model.Employee;
import com.example.simpleHttpService.repository.IEmployeesRepository;
import com.example.simpleHttpService.services.DataBaseFileStorageService;
import com.google.common.net.HttpHeaders;

@RestController
public class DataBaseFileController {
	@Autowired
	private IEmployeesRepository employeesRepository;
	
	@Autowired
	private DataBaseFileStorageService dbFileService;
	
	@PostMapping("/employees/{id}/uploadPhoto")
	public ResponseEntity<ResponseDto> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		Optional<Employee> empOpt = employeesRepository.findById(id);
		Employee emp;
		try {
			emp = empOpt.get();
		} catch(NoSuchElementException e) {
			return new ResponseEntity<>(new MessageResponseDto("No existe el empleado."), HttpStatus.NOT_FOUND);
		}
		
		DataBaseFile dbFile;
		
		try {
			dbFile = dbFileService.storeFile(file);
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageResponseDto("Ha habido un error."), HttpStatus.CONFLICT);
		}
		
		emp.setPhotoId(dbFile.getId());
		
		employeesRepository.save(emp);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("employees/"+id+"/downloadPhoto")
				.toUriString();
		
		return new ResponseEntity<>(new MessageResponseDto(dbFile.getFileName() + "; " + fileDownloadUri + "; " + file.getContentType() + "; " + file.getSize()), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}/downloadPhoto")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
		Optional<Employee> empOpt = employeesRepository.findById(id);
		
		if(!empOpt.isEmpty()) {
			Employee emp = empOpt.get();
			DataBaseFile dbFile = dbFileService.getFile(emp.getPhotoId());
			
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(dbFile.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"").
					body(new ByteArrayResource(dbFile.getData()));
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
