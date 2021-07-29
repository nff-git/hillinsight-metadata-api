package hillinsight.metadata.api.portal.demo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import focus.core.ResponseResult;
import hillinsight.metadata.api.utils.convention.UniqueUtils;
import hillinsight.metadata.api.utils.s3.AwsS3Util;
import hillinsight.metadata.api.utils.s3.S3Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @ClassName FileUploadByS3Controller
 * @Description TODO
 * @Author wcy
 * @Date 2021/3/3
 * @Version 1.0
 */

@RestController
@RequestMapping(path = "/file")
@Slf4j
public class FileUploadByS3Controller {


    @Autowired
    S3Config s3Config;

    /**
     * 文件负载测试
     *
     * @return {@link ResponseResult}
     */
    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
    public ResponseResult fileUpLoadTest(@RequestParam("file") MultipartFile file) {
        String fileKey = "";
        try {
            if (null == s3Config) throw new BuilderException("加载配置失败");
            fileKey = s3Config.getUploadKey() + UniqueUtils.getUUID();
            if (!AwsS3Util.uploadFileToS3(
                    s3Config.getBucket(), fileKey, file.getBytes())) throw new BuilderException("上传失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success("成功地址为：" + fileKey);
    }


    /**
     * s3下载文件
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/downFile", method = RequestMethod.GET)
    public void downFile(@RequestParam String fileId, HttpServletResponse response) throws IOException {
        if (StrUtil.isEmpty(fileId)) throw new BuilderException("文件id为空");
        byte[] bytes = AwsS3Util.downloadFile(s3Config.getBucket(), fileId);
        if (bytes == null) throw new BuilderException("文件不存在");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "thirdImportTemplate" + ".xls");
        ServletUtil.write(response, new ByteArrayInputStream(bytes));
    }
}
