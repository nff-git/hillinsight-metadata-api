package hillinsight.metadata.api.dto.web.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName 获取信息分组模板配置详情
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/17
 * @Version 1.0
 */
@Data
public class MsgGroupTempDetailReq {

    @NotNull(message = "信息分组模板id不能为空！")
    private int tempId;//信息分组模板id
}
