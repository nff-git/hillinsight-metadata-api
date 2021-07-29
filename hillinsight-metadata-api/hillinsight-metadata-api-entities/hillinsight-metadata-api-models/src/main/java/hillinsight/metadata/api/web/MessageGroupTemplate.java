package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName 页面信息分组模板表
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/14
 * @Version 1.0
 */
@Data
public class MessageGroupTemplate implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 业务对象名称
     */
    @NotBlank(message = "业务对象名称不能为空！")
    private String thirdObjName;

    /**
     * 业务字段名称
     */
    @NotBlank(message = "业务字段名称不能为空！")
    private String thirdFieldName;

    /**
     * 页面信息分组key
     */
    @NotBlank(message = "页面信息分组key不能为空！")
    private String messageGroupKey;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 配置json
     */
    private String configJson;

    /**
     * 是否更新过配置 1是 0否
     */
    private Integer isUpdateConfig;




}
