package com.mrityunjay.savefileindb.service;

import org.springframework.web.multipart.MultipartFile;

import com.mrityunjay.savefileindb.model.FileModel;

public interface SaveFileIntoDbService {
	public FileModel saveFileIntoDB(MultipartFile file);
	public FileModel getFile(String fileId);
}
