package hillinsight.metadata.api.dto.web;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName BusiSysResult 业务系统返回结果集
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/7
 * @Version 1.0
 */
public class BusiSysResult  implements Serializable {

    private String pagekey;

    private Map<String,Map<String,Object>> objects;

    public BusiSysResult() {
    }
    public BusiSysResult(String pagekey) {
        this.pagekey = pagekey;
    }

    public String getPagekey() {
        return pagekey;
    }

    public void setPagekey(String pagekey) {
        this.pagekey = pagekey;
    }

    public Map<String, Map<String, Object>> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Map<String, Object>> objects) {
        this.objects = objects;
    }
}
