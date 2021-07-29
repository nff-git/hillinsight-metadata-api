package hillinsight.metadata.api.dto.web.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName 更新信息分组模板排序请求体
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/17
 * @Version 1.0
 */
@Data
public class MsgGroupTempOrderReq {

    @NotNull(message = "第一个信息分组模板id不能为空")
    private int orderNumOne;//第一个信息分组模板id

    @NotNull(message = "第二个信息分组模板id不能为空")
    private int orderNumTwo;//第二个信息分组模板id

    @NotBlank(message = "key不能为空！")
    private String messageGroupKey;


}
