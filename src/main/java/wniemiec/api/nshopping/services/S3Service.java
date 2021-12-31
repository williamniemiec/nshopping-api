package wniemiec.api.nshopping.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wniemiec.api.nshopping.services.exceptions.FileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Responsible for providing Amazon S3 services.
 */
@Service
public class S3Service {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final Logger LOG;

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;


    //-------------------------------------------------------------------------
    //		Initialization blocks
    //-------------------------------------------------------------------------
    static {
        LOG = LoggerFactory.getLogger(S3Service.class);
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public URI uploadFile(MultipartFile multipartFile) {
        try {
            return uploadFile(
                multipartFile.getInputStream(), 
                multipartFile.getOriginalFilename(), 
                multipartFile.getContentType()
            );
        }
        catch (IOException e) {
            throw new FileException("IO Error: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String filename, 
                          String contentType) {
        ObjectMetadata metadata = generateMetadata(contentType);

        try {
            LOG.info("Uploading...");
            s3client.putObject(bucketName, filename, inputStream, metadata);
            LOG.info("Upload has finished");
            
            return s3client
                .getUrl(bucketName, filename)
                .toURI();
        }
        catch (URISyntaxException e) {
            throw new FileException("URL to URI");
        }
    }

    private ObjectMetadata generateMetadata(String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
      
        metadata.setContentType(contentType);

        return metadata;
    }
}
