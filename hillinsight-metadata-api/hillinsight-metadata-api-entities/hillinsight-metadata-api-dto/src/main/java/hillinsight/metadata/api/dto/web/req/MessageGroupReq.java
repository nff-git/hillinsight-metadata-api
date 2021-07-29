package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.MessageGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName 添加或更新信息分组请求体
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class MessageGroupReq {

    @Valid
    private MessageGroup messageGroup;



}
