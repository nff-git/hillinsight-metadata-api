package hillinsight.metadata.api.dto.web.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName UpdateMsgGrpTempConfigReq
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class UpdateMsgGrpTempConfigReq {

    @NotNull(message = "id不能为空！")
    private Integer id;

    @NotBlank(message = "配置Json不能为空！")
    private String configJson;
}
