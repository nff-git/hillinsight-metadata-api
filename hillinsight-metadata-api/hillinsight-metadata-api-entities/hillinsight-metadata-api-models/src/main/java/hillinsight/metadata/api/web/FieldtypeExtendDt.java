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
 * @ClassName 字段类型日期时间扩展表
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
@Data
public class FieldtypeExtendDt implements Serializable {


    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 是否选择日期1是0不是
     */
    private Integer isDate;

    /**
     * 是否选择时间1是0不是
     */
    private Integer isTime;

    /**
     * 是否选择星期1是0不是
     */
    private Integer isWeek;

    /**
     * 日期格式名称
     */
    private String dateFormatName;

    /**
     * 日期格式code
     */
    private Integer dateFormatCode;

    /**
     * 时间格式名称
     */
    private String timeFormatName;

    /**
     * 时间格式code
     */
    private Integer timeFormatCode;

    /**
     * 星期格式名称
     */
    private String weekFormatName;

    /**
     * 星期格式code
     */
    private Integer weekFormatCode;

    /**
     * 起始时间范围
     */
    private String startTimeScope;

    /**
     * 结束时间范围
     */
    private String endTimeScope;

    /**
     * 是否必填1是0不是
     */
    private Integer isMust;
    private Integer isMustDt;//获取excel值

    /**
     * 是否使用1是0不是
     */
//    @NotNull(message = "isEmploy不能为空！")
//    @Range(min = 0,max = 1)
    private Integer isEmploy;
    private Integer isEmployDt;//获取excel值


    /**
     * 是否使用1是0不是
     */
    private Integer fieldId;
    private Integer fieldIdDt;//获取excel值


}
