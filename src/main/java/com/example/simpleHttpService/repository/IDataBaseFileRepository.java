package com.example.simpleHttpService.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.simpleHttpService.model.DataBaseFile;


public interface IDataBaseFileRepository extends CrudRepository<DataBaseFile, String> {
	
}
