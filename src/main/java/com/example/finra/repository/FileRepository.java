package com.example.finra.repository;

import com.example.finra.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByNameContains(String fileName);

    List<File> findAllByDateIsAfter(Date before);
}
