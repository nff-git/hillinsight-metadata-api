package hillinsight.metadata.api.dto.web.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName DelMsgGroupReq
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class DelMsgGroupReq {

    @NotBlank(message = "信息分组key不能为空！")
    private String messageGroupKey;
    @NotNull(message = "页面id不能为空！")
    private Integer pageId;
}
