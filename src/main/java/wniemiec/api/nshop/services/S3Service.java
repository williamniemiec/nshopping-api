package wniemiec.api.nshop.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kinesisanalytics.model.Input;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wniemiec.api.nshop.services.exceptions.FileException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        InputStream inputStream = null;

        try {
            inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, filename, contentType);
        }
        catch (IOException e) {
            throw new FileException("IO Error: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String filename, String contentType) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(contentType);

        try {
            LOG.info("Uploading...");
            s3client.putObject(bucketName, filename, inputStream, meta);
            LOG.info("Upload has finished");
            return s3client.getUrl(bucketName, filename).toURI();
        }
        catch (URISyntaxException e) {
            throw new FileException("URL to URI");
        }
    }
}
