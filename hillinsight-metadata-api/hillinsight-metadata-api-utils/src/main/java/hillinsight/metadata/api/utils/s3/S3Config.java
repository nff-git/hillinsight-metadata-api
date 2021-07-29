package hillinsight.metadata.api.utils.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author wangcy
 * @version 1.0
 * @date 2021/2/1 10:44 上午
 */
@Component
@RefreshScope
public class S3Config {

    @Value("${s3.accessKey}")
    private String accessKey;
    @Value("${s3.secretKey}")
    private String secretKey;
    private String sessionToken = "";
    @Value("${s3.region}")
    private String region;
    @Value("${s3.bucket}")
    private String bucket;
    @Value("${s3.uploadKey}")
    private String uploadKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
    }
}
