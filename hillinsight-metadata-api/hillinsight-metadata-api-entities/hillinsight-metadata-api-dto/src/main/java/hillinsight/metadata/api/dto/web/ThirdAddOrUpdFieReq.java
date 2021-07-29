package hillinsight.metadata.api.dto.web;

import hillinsight.metadata.api.web.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.RichTextString;

import javax.validation.Valid;

/**
 * 请求添加或修改业务字段入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
@Data
public class ThirdAddOrUpdFieReq {

    @Valid
    private ThirdFieldInfo thirdFieldInfo;

    @Valid
    private FieldtypeExtendMoney fieldtypeExtendMoney;//金额

    @Valid
    private FieldtypeExtendPercent fieldtypeExtendPercent;//百分比

    @Valid
    private FieldtypeExtendText fieldtypeExtendText;//文本

    @Valid
    private FieldtypeExtendValue fieldtypeExtendValue;//数值

    @Valid
    private FieldtypeExtendDt fieldtypeExtendDt;//日期时间

}
