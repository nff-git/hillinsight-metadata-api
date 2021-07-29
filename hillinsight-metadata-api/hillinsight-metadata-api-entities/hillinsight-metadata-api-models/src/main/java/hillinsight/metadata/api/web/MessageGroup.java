package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName 页面信息分组表
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class MessageGroup extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 信息分组名称
     */
    @NotBlank(message = "信息分组名称不能为空！")
    private String messageGroupName;

    /**
     * 信息分组key
     */
    @NotBlank(message = "信息分组key不能为空！")
    private String messageGroupKey;

    /**
     * 页面id
     */
    @NotNull(message = "页面id不能为空！")
    private Integer pageId;

    @TableField(exist = false)
    private List<MessageGroupTemplate> messageGroupTemplates;//非数据库字段

    private List<MessageGroupTemplate> msgGroupTempList;//信息分组模板信息 非数据库字段

    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        super.setCreatorId(userCode);
        super.setCreatorName(userName);
        super.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }
}
