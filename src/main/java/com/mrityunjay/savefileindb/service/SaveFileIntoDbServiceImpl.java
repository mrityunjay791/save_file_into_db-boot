package com.mrityunjay.savefileindb.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mrityunjay.savefileindb.model.FileModel;
import com.mrityunjay.savefileindb.repository.SaveFileIntoDB;

@Service
public class SaveFileIntoDbServiceImpl implements SaveFileIntoDbService {

	@Autowired
	private SaveFileIntoDB saveFileIntoDB;

	@Override
	public FileModel saveFileIntoDB(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		FileModel fileObj = null;
		try {
			fileObj = new FileModel(fileName, file.getContentType(), file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return saveFileIntoDB.save(fileObj);
	}

	@Override
	public FileModel getFile(String fileId) {
		 Optional<FileModel> findById = saveFileIntoDB.findById(fileId);
		return findById.get();
	}

}
