package com.example.simpleHttpService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employeesPhotos")
public class DataBaseFile {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String fileName;
	
	private String fileType;
	
	@Lob
	private byte[] data;
	
	public DataBaseFile() {
		
	}
	
	public DataBaseFile(String fileName, String fileType, byte [] data) {
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.data = data;
	}
	
	public String getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public byte[] getData() {
		return data;
	}
}
