package com.example.finra.controller;

import com.example.finra.model.File;
import com.example.finra.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/getFileMetaData")
    public ResponseEntity<File> getFileMetaData(
            @RequestParam Long fileId){

        Optional<File> file = fileService.getFile(fileId);
        if(!file.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(file.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getFile", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFile(
            @RequestParam Long fileId){

        Optional<File> file = fileService.getFile(fileId);
        if(!file.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get().getName() + "\"")
                .contentType(MediaType.parseMediaType(file.get().getContentType()))
                .body(new ByteArrayResource(file.get().getData()));
    }

    @GetMapping(value = "/getFileIds")
    public ResponseEntity<Long[]> getFileIds(
            @RequestParam String fileName){

        List<File> file = fileService.getFileIds(fileName);
        if(ObjectUtils.isEmpty(file)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long [] ids = file.stream().map(File::getId).toArray(Long[]::new);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> uploadFile(
            @RequestPart MultipartFile multipartFile){
        File savedFile;
        try {
            File file = File.builder()
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .data(multipartFile.getBytes())
                    .name(multipartFile.getOriginalFilename())
                    .build();
            savedFile = fileService.uploadFile(file);
        } catch (IOException e) {
            log.error("IOExcetpion:{}", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedFile,HttpStatus.OK);
    }
}
