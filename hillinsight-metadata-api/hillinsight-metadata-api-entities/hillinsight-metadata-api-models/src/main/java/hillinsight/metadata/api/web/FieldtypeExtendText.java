package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @ClassName 字段类型文本扩展表
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
@Data
public class FieldtypeExtendText implements Serializable {


    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 行类型code
     */
    private Integer lineTypeCode;

    /**
     * 行类型名称
     */
    private String lineTypeName;

    /**
     * 最大字数限制
     */
    private String maxNumberLimit;

    /**
     * 格式校验code
     */
    private Integer formatValidataCode;

    /**
     * 格式校验名称
     */
    private String formatValidataName;

    /**
     * 默认值
     */
    private String defaultValue;
    private String defaultValueText;//获取excel字段值
    /**
     * 是否必填1是0不是
     */
    private Integer isMust;
    private Integer isMustText;//获取excel字段值

    /**
     * 是否使用1是0不是
     */
//    @NotNull(message = "isEmploy不能为空")
//    @Range(min =0,max = 1)
    private Integer isEmploy;
    private Integer isEmployText;//获取excel字段值

    /**
     * 字段id
     */
    private Integer fieldId;
    private Integer fieldIdText;//获取excel字段值


}
