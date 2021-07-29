package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.MessageGroupTemplate;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName MessageGroupTempReq
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class MessageGroupTempReq {

    private List<MessageGroupTemplate> messageGroupTemplateList;

    /**
     * 页面信息分组key
     */
    @NotBlank(message = "页面信息分组key不能为空！")
    private String messageGroupKey;



}
