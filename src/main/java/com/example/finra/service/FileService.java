package com.example.finra.service;

import com.example.finra.model.File;
import com.example.finra.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public File uploadFile(File file){
       return fileRepository.save(file);
    }

    public Optional<File> getFile(Long fileId){
        return fileRepository.findById(fileId);
    }

    public List<File> getFileIds(String fileName){
        return fileRepository.findAllByNameContains(fileName);
    }
}
