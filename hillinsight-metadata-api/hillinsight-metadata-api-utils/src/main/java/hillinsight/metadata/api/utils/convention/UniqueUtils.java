package hillinsight.metadata.api.utils.convention;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName UniqueUtils
 * @Description TODO 页面配置 page key 生成器
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class UniqueUtils {

    public  static String  getpageKey(String pre){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);
        return  pre+format;
    }

    /**
     * 得到uuid
     *
     * @return {@link String}
     */
    public  static  String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
