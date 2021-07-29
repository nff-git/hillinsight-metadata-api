package hillinsight.metadata.api.dto.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @ClassName BusiSysResultV2
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/18
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class BusiSysResultV2 {

    private String pagekey;

    private Map<String, Map<String,Object>> msgGroups;

    public BusiSysResultV2(String pagekey) {
        this.pagekey = pagekey;
    }
}
