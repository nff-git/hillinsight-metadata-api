package hillinsight.metadata.api.dto.web.req;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.web.MetadataPageInfo;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import lombok.var;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2021/07/06
 */
@Component
public class OssTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractPollThread.class);

    @Autowired
    PollThreadConfig plll;
    public static void main2(String[] args) {
        /*上面那个参数第一个是延迟值 延迟几秒也就是 initialDelay，delay是间隔多少秒查一次，timeout那 是设置的超时时间，超过设置的 无论失败成功都会走完*/
        /*你也可查查 ScheduledExecutorService.scheduleWithFixedDelay 这个方法*/

        String devNum ="test";
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        final Map<String, Future> futures = new HashMap<>();
        // 每隔10秒进行轮询
        final ScheduledFuture<?> future = service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                //这个是我用的先给你注释掉
                //String task = AliyunOSS.selectVideoFilePath("f900bd1137f5461ba602bdd81b35fe0e");
                String task = "1";
                //看时间
                log.info("time:{}",new Date());
                //task为空 永远进不去 为了模拟错误 这样可以轮询6次每十秒钟 超出1分钟自动关闭
                if (task != null && task.length() != 0) {
                    Future future = futures.get(devNum);
                    if (future != null) {
                        futures.remove(devNum);
                        future.cancel(true);
                        service.shutdown();
                    }
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
        try {
            futures.put(devNum, future);
            // 设置超时时间
            future.get(60, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            log.error(e2.getMessage());
        } catch (CancellationException e2) {
            log.info(e2.getMessage());
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            log.error(e2.getMessage());
        } catch (TimeoutException e2) {
            log.info(e2.getMessage());
        } finally {
            future.cancel(true);
            service.shutdownNow();
        }
    }

    public static void main(String[] args) {

        String result = "{\"requestId\":\"600D41EA-DE50-4CFB-975F-1E07C3EBF209\",\"nonExistJobIds\":[],\"jobList\":[{\"output\":{\"mergeList\":[],\"container\":{\"format\":\"mp4\"},\"superReso\":{},\"openingList\":[],\"video\":{\"codec\":\"H.264\",\"width\":\"1280\",\"fps\":\"25\",\"bitrate\":\"1500\",\"bitrateBnd\":{}},\"templateId\":\"S00000001-200020\",\"priority\":\"6\",\"outSubtitleList\":[],\"muxConfig\":{\"gif\":{},\"segment\":{},\"webp\":{}},\"tailSlateList\":[],\"waterMarkList\":[],\"outputFile\":{\"bucket\":\"shu-cu-mei-ti\",\"location\":\"oss-cn-shenzhen\",\"object\":\"test%2F%E8%BD%AC%E7%A0%81%E8%A7%86%E9%A2%91%2Fupload%2FIMG_1327.MOV%2Ftest.mp4\"},\"encryption\":{},\"subtitleConfig\":{\"extSubtitleList\":[],\"subtitleList\":[]},\"audio\":{\"volume\":{},\"codec\":\"AAC\",\"channels\":\"2\",\"bitrate\":\"128\",\"samplerate\":\"44100\"},\"m3U8NonStandardSupport\":{\"tS\":{}},\"properties\":{\"streams\":{\"audioStreamList\":[],\"videoStreamList\":[],\"subtitleStreamList\":[]},\"format\":{},\"sourceLogos\":[]},\"clip\":{\"timeSpan\":{}},\"transConfig\":{}},\"jobId\":\"653a4357501a43db940d4a527a71776f\",\"input\":{\"bucket\":\"shu-ru-mei-ti\",\"location\":\"oss-cn-shenzhen\",\"object\":\"upload%2FIMG_1327.MOV\"},\"creationTime\":\"2021-07-07T02:04:42Z\",\"mNSMessageResult\":{},\"state\":\"Transcoding\",\"percent\":20,\"pipelineId\":\"c900bcb9ac92443bb7517d430df6e646\"}]}";
        JSONObject json = JSONObject.parseObject(result);
        String jobList =json.get("jobList").toString();


        String firstChar = jobList.substring(1);
        String substring = firstChar.substring(0, firstChar.length() -1);
//        System.out.println(substring);
        JSONObject json2 = JSONObject.parseObject(substring);
        String output = json2.get("output").toString();

        JSONObject json3 = JSONObject.parseObject(output);
        String outputFile = json3.get("outputFile").toString();

        JSONObject json4 = JSONObject.parseObject(outputFile);
        String object = json4.get("object").toString();



        PollThreadConfig pollThreadConfig = new PollThreadConfig();
        System.out.println("AccessTime======"+ pollThreadConfig.getAccessTime());




//        String result = "{\"requestId\":\"600D41EA-DE50-4CFB-975F-1E07C3EBF209\",\"nonExistJobIds\":[],\"jobList\":[{\"output\":{\"mergeList\":[],\"container\":{\"format\":\"mp4\"},\"superReso\":{},\"openingList\":[],\"video\":{\"codec\":\"H.264\",\"width\":\"1280\",\"fps\":\"25\",\"bitrate\":\"1500\",\"bitrateBnd\":{}},\"templateId\":\"S00000001-200020\",\"priority\":\"6\",\"outSubtitleList\":[],\"muxConfig\":{\"gif\":{},\"segment\":{},\"webp\":{}},\"tailSlateList\":[],\"waterMarkList\":[],\"outputFile\":{\"bucket\":\"shu-cu-mei-ti\",\"location\":\"oss-cn-shenzhen\",\"object\":\"test%2F%E8%BD%AC%E7%A0%81%E8%A7%86%E9%A2%91%2Fupload%2FIMG_1327.MOV%2Ftest.mp4\"},\"encryption\":{},\"subtitleConfig\":{\"extSubtitleList\":[],\"subtitleList\":[]},\"audio\":{\"volume\":{},\"codec\":\"AAC\",\"channels\":\"2\",\"bitrate\":\"128\",\"samplerate\":\"44100\"},\"m3U8NonStandardSupport\":{\"tS\":{}},\"properties\":{\"streams\":{\"audioStreamList\":[],\"videoStreamList\":[],\"subtitleStreamList\":[]},\"format\":{},\"sourceLogos\":[]},\"clip\":{\"timeSpan\":{}},\"transConfig\":{}},\"jobId\":\"653a4357501a43db940d4a527a71776f\",\"input\":{\"bucket\":\"shu-ru-mei-ti\",\"location\":\"oss-cn-shenzhen\",\"object\":\"upload%2FIMG_1327.MOV\"},\"creationTime\":\"2021-07-07T02:04:42Z\",\"mNSMessageResult\":{},\"state\":\"Transcoding\",\"percent\":20,\"pipelineId\":\"c900bcb9ac92443bb7517d430df6e646\"}]}";
//        JSONObject json =JSONObject.parseObject(result);
//        String jobList = json.getString("jobList");
//        String firstChar = jobList.substring(1);
//        String substring = firstChar.substring(0, firstChar.length() -1);
//        String output =JSONObject.parseObject(substring).getString("output");
//        String outputFile =JSONObject.parseObject(output).getString("outputFile");
//        String fileName =JSONObject.parseObject(outputFile).getString("object");
//        System.out.println(fileName);
    }

}
