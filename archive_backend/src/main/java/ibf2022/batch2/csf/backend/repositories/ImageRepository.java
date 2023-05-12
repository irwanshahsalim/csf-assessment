package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.services.S3Service;

@Repository
public class ImageRepository {

	@Autowired
	private S3Service s3Service;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public List<String> upload(MultipartFile file) throws IOException {
		return s3Service.unzipToS3(file);
	}
}
