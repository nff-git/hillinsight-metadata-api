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
 * @ClassName 字段类型百分比扩展
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
@Data
public class FieldtypeExtendPercent  implements Serializable {


    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 输入数值方式code
     */
    private Integer inputNumberWayCode;

    /**
     * 输入数值方式名称
     */
    private String inputNumberWayName;

    /**
     * 保存小数位数
     */
    private Integer saveDecimalsPlaces;
    private Integer saveDecimalsPlacesPercent;

    /**
     * 数值范围最小
     */
    private String numberScopeMin;

    /**
     * 数值范围最大
     */
    private String numberScopeMax;

    /**
     * 默认值
     */
    private String defaultValue;
    private String defaultValuePercent;//获取excel值

    /**
     * 是否必填1是0不是
     */
    private Integer isMust;
    private Integer isMustPercent;//获取excel值

    /**
     * 是否使用1是0不是
     */
//    @NotNull(message = "isEmploy不能为空")
//    @Range(min =0,max = 1)
    private Integer isEmploy;
    private Integer isEmployPercent;//获取excel值

    /**
     * 字段id
     */
    private Integer fieldId;
    private Integer fieldIdPercent;//获取excel值



}
