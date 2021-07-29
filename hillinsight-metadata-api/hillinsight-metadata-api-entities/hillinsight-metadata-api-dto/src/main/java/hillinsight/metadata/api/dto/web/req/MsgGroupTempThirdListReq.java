package hillinsight.metadata.api.dto.web.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName MsgGroupTempThirdListReq
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class MsgGroupTempThirdListReq {

    @NotBlank(message = "系统来源id不能为空！")
    private String sourceId;
}
