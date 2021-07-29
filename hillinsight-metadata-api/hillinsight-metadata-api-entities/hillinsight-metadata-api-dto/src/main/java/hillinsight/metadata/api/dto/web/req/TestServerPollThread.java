package hillinsight.metadata.api.dto.web.req;

import java.sql.Timestamp;

/**
 * @Description: 测试轮询线程
 * @author: scott
 * @date: 2021/07/06
 */
public class TestServerPollThread extends AbstractPollThread{

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractPollThread.class);

    /**
     * 插件     - OnlineServerManagerPlugin -启动的时候初始化线程
     * @param threadId 轮询线程ID
     * @param threadName 轮询线程名称
     * @param longname  中文名称
     * @param delay 轮询线程首次延迟时间
     * @param timeInterval 时间间隔
     */
    public TestServerPollThread(String threadName, String threadId, String longname, Long delay, Long timeInterval) {
        super(threadName, threadId, longname, delay, timeInterval);
    }

    /**
     * 轮询间隔回调方法
     */
    @Override
    public void process() {
        log.info("刷新时间为：{}", new Timestamp(System.currentTimeMillis()));
        //逻辑
    }



}
