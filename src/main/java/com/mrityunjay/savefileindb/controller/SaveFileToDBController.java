package com.mrityunjay.savefileindb.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mrityunjay.savefileindb.model.FileModel;
import com.mrityunjay.savefileindb.model.Response;
import com.mrityunjay.savefileindb.service.SaveFileIntoDbService;

/**
 * 
 * @author mrityunjaykumar
 *
 */
@RestController
public class SaveFileToDBController {

	@Autowired
	private SaveFileIntoDbService saveFileIntoDbService;

	/**
	 * Used to save file into DB..
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/upload-file")
	public Response saveFileToDb(@RequestParam("file") MultipartFile file) {
		FileModel fileModel = saveFileIntoDbService.saveFileIntoDB(file);

		String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileModel.getId()).toUriString();

		return new Response(fileModel.getFileName(), downloadUri, fileModel.getFileType(), file.getSize());

	}

	/**
	 * Used to save multiple file into DB.
	 * 
	 * @param files
	 * @return
	 */
	@PostMapping("/upload-multiple-files")
	public List<Response> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> saveFileToDb(file)).collect(Collectors.toList());
	}
	
	/**
	 * This request will be used for downloading the file..
	 * 
	 * @param fileId
	 * @return
	 */
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		FileModel fileModel = saveFileIntoDbService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileModel.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: fileName=\"" + fileModel.getFileName() + "\"")
				.body(new ByteArrayResource(fileModel.getByteValue()));
	}
}
