package com.mrityunjay.savefileindb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrityunjay.savefileindb.model.FileModel;

@Repository
public interface SaveFileIntoDB extends JpaRepository<FileModel, String> {

}
