package hillinsight.metadata.api.models.vo;


import hillinsight.metadata.api.web.*;
import lombok.Data;

import java.io.Serializable;


/**
 * @ClassName 业务字段导入copy实体
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/24
 * @Version 1.0
 */
@Data
public class ThirdFieldExcelimportCopy implements Serializable {

    private String idNum;

    private String fieldName;//字段名 相当于 业务方字段id

    private String fieldTypeName;//字段类型  名称

    private String showNameCn;//字段显示名(中文)

    private String showNameEn;//字段显示名(英文)

    private String metadataObjName;//元数据对象名

    private String metadataFieldName;//元数据字段名称

    private Integer isExtension;//是否为 自定义字段 1 是 0不是

    private String extensionName;//自定义字段名称

    private String misTakeMsg;//错误信息

    private String fieldTypeCode;//字段类型 code值

    private ThirdFieldInfo thirdFieldInfo; //业务字段信息

    private String misTakeMsgMoney;//金额错误信息

    private String misTakeMsgValue;//数值错误信息

    private String misTakeMsgPercent;//百分比错误信息

    private String misTakeMsgText;//文本错误信息

    private String misTakeMsgDt;//日期时间错误信息

    //时间
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
     * 时间格式名称
     */
    private String timeFormatName;

    /**
     * 星期格式名称
     */
    private String weekFormatName;

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
    private Integer isMustDt;

    /**
     * 是否使用1是0不是
     */
    private Integer isEmployDt;

    /**
     * 字段id
     */
    private Integer fieldIdDt;

    //金额
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
    private String defaultValueMoney;

    /**
     * 是否必填1是0不是
     */
    private Integer isMustMoney;

    /**
     * 是否使用1是0不是
     */
    private Integer isEmployMoney;

    /**
     * 字段id
     */
    private Integer fieldIdMoney;

    /**
     * 是否显示币种
     */
    private Integer isShowCurrency;

    //百分比
    /**
     * 输入数值方式名称
     */
    private String inputNumberWayName;

    /**
     * 保存小数位数
     */
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
    private String defaultValuePercent;

    /**
     * 是否必填1是0不是
     */
    private Integer isMustPercent;

    /**
     * 是否使用1是0不是
     */
    private Integer isEmployPercent;

    /**
     * 字段id
     */
    private Integer fieldIdPercent;

    //文本
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
    private String defaultValueText;

    /**
     * 是否必填1是0不是
     */
    private Integer isMustText;

    /**
     * 是否使用1是0不是
     */
    private Integer isEmployText;

    /**
     * 字段id
     */
    private Integer fieldIdText;

    //数值
    /**
     * 是否显示千分位
     */
    private Integer showThousandsValue;

    /**
     * 保存小数位数
     */
    private Integer saveDecimalsPlacesValue;

    /**
     * 单位显示名中文
     */
    private String unitShownameCnValue;

    /**
     * 单位显示名英文
     */
    private String unitShownameEnValue;

    /**
     * 单位显示位置code
     */
    private Integer unitShowlocationCodeValue;

    /**
     * 单位显示位置name
     */
    private String unitShowlocationNameValue;

    /**
     * 默认值
     */
    private String defaultValueValue;

    /**
     * 是否必填1是0不是
     */
    private Integer isMustValue;

    /**
     * 是否使用1是0不是
     */
    private Integer isEmployValue;

    /**
     * 倍数转换
     */
    private String multipleTransformValue;

    /**
     * 数值最小范围
     */
    private String numberScopeMinValue;

    /**
     * 数值最大范围
     */
    private String numberScopeMaxValue;

    /**
     * 字段id
     */
    private Integer fieldIdValue;

    private FieldtypeExtendDt fieldtypeExtendDt; //时间

    private FieldtypeExtendMoney fieldtypeExtendMoney;//金额

    private FieldtypeExtendPercent fieldtypeExtendPercent; //百分比

    private FieldtypeExtendText fieldtypeExtendText;//文本

    private FieldtypeExtendValue fieldtypeExtendValue;//数值

}
