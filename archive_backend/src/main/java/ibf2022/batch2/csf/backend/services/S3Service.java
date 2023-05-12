package ibf2022.batch2.csf.backend.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    private AmazonS3 s3Client;
    private String bucketName;

    public S3Service(AmazonS3 s3Client, @Value("${do.spaces.bucketName}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }


    public List<String> unzipToS3(MultipartFile file) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();

        // Unzipping
        ZipInputStream zipStream = new ZipInputStream(file.getInputStream());
        ZipEntry entry = null;

        while ((entry = zipStream.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                String fileName = entry.getName();

                // Read the file data into a byte array
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipStream.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                byte[] fileData = out.toByteArray();

                // Preparing the object metadata
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(fileData.length);
                if (fileName.endsWith(".png")) {
                    metadata.setContentType("image/png");
                } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    metadata.setContentType("image/jpeg");
                } else if (fileName.endsWith(".gif")) {
                    metadata.setContentType("image/gif");
                }

                // Uploading the file
                s3Client.putObject(new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(fileData), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                
                uploadedFiles.add(s3Client.getUrl(bucketName, fileName).toString());
            }
            zipStream.closeEntry();
        }

        return uploadedFiles;
    }
}
