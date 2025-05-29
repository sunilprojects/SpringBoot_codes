package com.ats.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ResumeScreenService {
	  private static final String UPLOAD_DIR = "resumes/";

	    public String saveResumeToFileSystem(MultipartFile resumeFile) throws IOException {
	        String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
	        String filePath = UPLOAD_DIR + fileName;

	        File dir = new File(UPLOAD_DIR);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        try (OutputStream os = new FileOutputStream(filePath)) {
	            os.write(resumeFile.getBytes());
	        }

	        return filePath;
	    }

	    public String extractTextFromResume(File file) throws IOException {
	        Tika tika = new Tika();
	        return tika.parseToString(file);
	    }
	

}
