package hillinsight.metadata.api.dto.web;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @ClassName MsgGroupTempDetailResult
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/17
 * @Version 1.0
 */
@Data
public class MsgGroupTempDetailResult {

    private String fieldName;//字段名 相当于 业务方字段id
    private String showNameCn;//字段显示名(中文)
    private String showNameEn;//字段显示名(英文)
    private String fieldParaphraseCn;//字段释义中文
    private String fieldParaphraseEn;//字段释义英文
    private String metaFieldName;//元数据字段名
    private String fieldTypeCode;//字段类型 code值
    private String fieldTypeName;//字段类型  名称
//    private String ThirdObjshowNameCn;
//    private String ThirdObjshowNameEn;

    private JSONObject fieldTypeExtendMap;//高级配置
}
