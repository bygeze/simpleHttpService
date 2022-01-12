package com.example.simpleHttpService.services;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleHttpService.model.DataBaseFile;
import com.example.simpleHttpService.repository.IDataBaseFileRepository;

@Service
public class DataBaseFileStorageService {
	@Autowired
	private IDataBaseFileRepository dbFileRepository;
	
	public DataBaseFile storeFile(MultipartFile file) throws IOException {
		/* normalize name of the file
		 */
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DataBaseFile dbFile = new DataBaseFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName + ". Please try again!");
        }
        
        
	}
	
    public DataBaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileSystemNotFoundException("File not found with id " + fileId));
    }
}
