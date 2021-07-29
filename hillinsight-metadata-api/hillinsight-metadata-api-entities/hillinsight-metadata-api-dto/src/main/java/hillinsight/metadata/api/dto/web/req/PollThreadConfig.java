package hillinsight.metadata.api.dto.web.req;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 轮询线程配置
 * @author: scott
 * @date: 2021/07/06
 */
@Data
@Component
//@ConfigurationProperties(prefix = "thread")
public class PollThreadConfig {

    private static final long serialVersionUID = 1L;

    /**
     * 轮询线程ID
     */
//    @Value("${thread.threadId}")
    private String threadId;

    /**
     * 轮询线程名称
     */
//    @Value("${thread.threadName}")
    private String threadName;

    /**
     * 间隔时间
     */
//    @Value("${thread.timeInterval}")
    private Long timeInterval;

    /**
     * 轮询线程首次延迟时间
     */
//    @Value("${thread.delay}")
    private Long delay;

//    thread:
//    enabled: true
//    threadId: LSRThread
//    threadName: 轮询线程
//    timeInterval: 20
//    delay: 10
//
//    oss:
//    valid:
//    time: 10
//    access:
//    time: 900
//    va:
//    time: 600

    public static Integer accessTime;

    public static Integer getAccessTime() {
        return accessTime;
    }
//    @Value("${oss.access.time}")
    public void setAccessTime(Integer accessTime) {
        PollThreadConfig.accessTime = accessTime;
    }

    public static void main(String[] args) {
        System.out.println(accessTime);
    }

}
