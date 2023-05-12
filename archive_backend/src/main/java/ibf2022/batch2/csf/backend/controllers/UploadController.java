package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@RestController
@RequestMapping(path = "/upload")
public class UploadController {

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private ArchiveRepository archiveRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> handleUpload(
	@RequestPart String name, 
	@RequestPart String title,
	@RequestPart String comments, 
	@RequestPart MultipartFile file) throws IOException{

		try {
            List<String> uploadedFiles = imageRepo.upload(file);
            Archive archive = archiveRepo.recordBundle(name, title, comments, uploadedFiles);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"bundleId\":\"" + archive.getBundleId() + "\"}");
        } catch (Exception e) {
            String errorMessage = "Unable to process file: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"" + errorMessage + "\"}");
        }
    }
	

	// TODO: Task 5
	@GetMapping("/bundle/{bundleId}")
    public ResponseEntity<?> getBundle(@PathVariable String bundleId) {
        Archive archive = archiveRepo.getBundleByBundleId(bundleId);
        if (archive == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Bundle not found.\"}");
        }
        return ResponseEntity.ok(archive);
    }


	// TODO: Task 6

}