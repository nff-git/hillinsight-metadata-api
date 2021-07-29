package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import lombok.Data;
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
public class FieldtypeExtendMoney implements Serializable {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 是否显示千分位
     */
    private Integer showThousands;

    /**
     * 单位转换code
     */
    private Integer unitTransformCode;

    /**
     * 单位转换name
     */
    private String unitTransformName;

    /**
     * 保存小数位数
     */
    private Integer saveDecimalsPlaces;

    /**
     * 单位显示名中文
     */
    private String unitShownameCn;

    /**
     * 单位显示名英文
     */
    private String unitShownameEn;

    /**
     * 单位显示位置code
     */
    private Integer unitShowlocationCode;

    /**
     * 单位显示位置name
     */
    private String unitShowlocationName;

    /**
     * 金额范围最小
     */
    private String moneyScopeMin;

    /**
     * 金额范围最大
     */
    private String moneyScopeMax;

    /**
     * 默认值
     */
    private String defaultValue;
    private String defaultValueMoney;//获取excel字段值


    /**
     * 是否必填1是0不是
     */
    private Integer isMust;
    private Integer isMustMoney;//获取excel字段值

    /**
     * 是否使用1是0不是
     */
//    @NotNull(message = "isEmploy不能为空")
//    @Range(min =0,max = 1)
    private Integer isEmploy;
    private Integer isEmployMoney;//获取excel字段值

    /**
     * 字段id
     */
    private Integer fieldId;
    private Integer fieldIdMoney;//获取excel字段值

    /**
     * 是否显示币种
     */
    private Integer isShowCurrency;

}
