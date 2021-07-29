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
 * @ClassName 字段类型数值扩展表
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
@Data
public class FieldtypeExtendValue  implements Serializable {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 是否显示千分位
     */
    private Integer showThousands;
    private Integer showThousandsValue;//获取excel字段值

    /**
     * 保存小数位数
     */
    private Integer saveDecimalsPlaces;
    private Integer saveDecimalsPlacesValue;//获取excel字段值

    /**
     * 单位显示名中文
     */
    private String unitShownameCn;
    private String unitShownameCnValue;//获取excel字段值

    /**
     * 单位显示名英文
     */
    private String unitShownameEn;
    private String unitShownameEnValue;//获取excel字段值

    /**
     * 单位显示位置code
     */
    private Integer unitShowlocationCode;
    private Integer unitShowlocationCodeValue;//获取excel字段值

    /**
     * 单位显示位置name
     */
    private String unitShowlocationName;
    private String unitShowlocationNameValue;//获取excel字段值

    /**
     * 默认值
     */
    private String defaultValue;
    private String defaultValueValue;//获取excel字段值

    /**
     * 是否必填1是0不是
     */
    private Integer isMust;
    private Integer isMustValue;//获取excel字段值

    /**
     * 是否使用1是0不是
     */
//    @NotNull(message = "isEmploy不能为空")
//    @Range(min =0,max = 1)
    private Integer isEmploy;
    private Integer isEmployValue;//获取excel字段值

    /**
     * 倍数转换
     */
    private String multipleTransform;
    private String multipleTransformValue;//获取excel字段值

    /**
     * 数值最小范围
     */
    private String numberScopeMin;
    private String numberScopeMinValue;//获取excel字段值

    /**
     * 数值最大范围
     */
    private String numberScopeMax;
    private String numberScopeMaxValue;//获取excel字段值

    /**
     * 字段id
     */
    private Integer fieldId;
    private Integer fieldIdValue;//获取excel字段值


}
