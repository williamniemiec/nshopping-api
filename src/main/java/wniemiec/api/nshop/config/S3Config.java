package wniemiec.api.nshop.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.s3.AmazonS3;


/**
 * Responsible for configuring Amazon S3.
 */
@Configuration
public class S3Config {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Value("${aws.access_key_id}")
    private String awsId;

    @Value("${aws.secret_access_key}")
    private String awsKey;

    @Value("${s3.region}")
    private String region;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Bean
    public AmazonS3 s3client() {

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(generateCredentials())
            .build();
    }

    private AWSCredentialsProvider generateCredentials() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsId, awsKey);
        
        return new AWSStaticCredentialsProvider(credentials);
    }
}
