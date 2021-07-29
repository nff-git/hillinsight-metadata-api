package hillinsight.metadata.api.utils.s3;

import cn.hutool.core.util.StrUtil;
import focus.core.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

/**
 * TODO
 *
 * @author wangcy
 * @version 1.0
 * @date 2021/2/1 10:43 上午
 */
@Component
public class AwsS3Util {

    public static final Logger LOGGER = LoggerFactory.getLogger(AwsS3Util.class);

    public static S3Client s3Client;

    public static S3Presigner s3Presigner;

    @Autowired
    S3Config s3Config;

    @PostConstruct
    public void init() {
        if (!StrUtil.isEmpty(s3Config.getAccessKey()) && !StrUtil.isEmpty(s3Config.getSecretKey())) {
            // 资格证书
            AwsSessionCredentials credentials = AwsSessionCredentials.create(s3Config.getAccessKey(), s3Config.getSecretKey(), s3Config.getSessionToken());
            StaticCredentialsProvider provider = StaticCredentialsProvider.create(credentials);
            Region region = Region.of(s3Config.getRegion());
            // 创建客户端
            s3Client = S3Client
                    .builder()
                    .region(region) //桶所在的位置
                    .credentialsProvider(provider)
                    .build();
            // 预签名客户端
            s3Presigner = S3Presigner
                    .builder()
                    .region(region)
                    .credentialsProvider(provider)
                    .build();
        }
    }

    /**
     * 上传文件到S3
     *
     * @param bucket
     * @param uploadKey
     * @param file
     * @return
     */
    public static  boolean uploadFileToS3(String bucket, String uploadKey, byte[] file) {
        if (file == null) {
            throw new BusinessException("upload file is null");
        }
        try {
            LOGGER.info("upload file to s3 start");
            long start = System.currentTimeMillis();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(uploadKey).build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
            LOGGER.info("upload file to s3 end, cost {} ms", System.currentTimeMillis() - start);
            return true;
        } catch (S3Exception e) {
            LOGGER.error("upload file to s3 error: {}", e);
            throw new BusinessException("upload file to s3 error: {}", e);
        }
    }

    public static byte[] downloadFile(String bucket, String uploadKey) {
        try {
            LOGGER.info("download file to s3 start");
            long start = System.currentTimeMillis();
            GetObjectRequest objectRequest = GetObjectRequest.builder().key(uploadKey).bucket(bucket).build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();
            LOGGER.info("download file to s3 end, cost {} ms", System.currentTimeMillis() - start);
            return data;
        } catch (Exception e) {
            LOGGER.error("downloadFile error: {}", e);
            throw new BusinessException("downloadFile error: {}", e.getMessage());
        }
    }

    /**
     * 获取可过期的文件下载url
     *
     * @param bucket
     * @param fileName
     * @return
     */
    public String getExpireDownloadUrl(String bucket, String fileName) {
        try {
            LOGGER.info("get expire download url start");
            long start = System.currentTimeMillis();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(fileName).build();
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5000l)).getObjectRequest(getObjectRequest).build();
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
            URL url = presignedGetObjectRequest.url();
            if (url != null) {
                LOGGER.info("get expire download url end, cost {} ms", System.currentTimeMillis() - start);
                return url.toString();
            }
        } catch (Exception e) {
            LOGGER.error("get expire download url: {}", e);
        }
        return "";
    }

    /**
     * 获取文件元数据
     *
     * @param bucket
     * @param fileName
     * @return
     */
    public HeadObjectResponse getMetaData(String bucket, String fileName) {
        HeadObjectRequest build = HeadObjectRequest.builder().bucket(bucket).key(fileName).build();
        HeadObjectResponse headObjectResponse = s3Client.headObject(build);
        return headObjectResponse;
    }

    /**
     * 获取最后修改时间，精确到秒
     *
     * @param bucket
     * @param fileName
     * @return
     */
    public long getLastModified(String bucket, String fileName) {
        HeadObjectResponse metaData = getMetaData(bucket, fileName);
        Instant instant = metaData.lastModified();
        return instant.getEpochSecond();
    }

}
