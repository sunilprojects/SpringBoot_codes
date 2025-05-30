package com.zoho.ats.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
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
	    
	    public List<String> extractMatchingSkills(String resumeText, String requiredSkills) {
	        Set<String> resumeWords = Arrays.stream(resumeText.toLowerCase().split("\\W+"))
	                                        .collect(Collectors.toSet());

	        Set<String> required = Arrays.stream(requiredSkills.toLowerCase().split(","))
	                                     .map(String::trim)
	                                     .collect(Collectors.toSet());

	        return required.stream()
	                       .filter(resumeWords::contains)
	                       .collect(Collectors.toList());
	    }


	    public String extractTextFromResume(File file) throws IOException, TikaException {
	        Tika tika = new Tika();
	        return tika.parseToString(file);
	    }
	

}
