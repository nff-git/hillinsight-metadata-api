package hillinsight.metadata.api.dto.web;

import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName 获取信息分组模板 所选业务配置列表 返回结果
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/17
 * @Version 1.0
 */
@Data
public class MsgGroupTempThirdListResult {

    private ThirdObjectInfo thirdObjectInfo;

    private List<ThirdFieldInfo> thirdFieldInfos;


}
