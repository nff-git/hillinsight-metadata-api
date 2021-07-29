package hillinsight.metadata.api.models.vo;

import hillinsight.metadata.api.models.BaseModel;
import hillinsight.metadata.api.web.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


/**
 * @ClassName ThirdFieldExcelimportVo
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/24
 * @Version 1.0
 */
public class ThirdFieldExcelimportVo extends BaseModel {

    private String idNum;

    private Integer id;

    private String fieldName;//字段名 相当于 业务方字段id

    private String fieldTypeName;//字段类型  名称

    private String showNameCn;//字段显示名(中文)

    private String showNameEn;//字段显示名(英文)

    private String metadataObjName;//元数据对象名

    private String metadataFieldName;//元数据字段名称

    private Integer isExtension;//是否为 自定义字段 1 是 0不是

    private String extensionName;//自定义字段名称

    private String fieldTypeCode;//字段类型 code值

    private ThirdFieldInfo thirdFieldInfo; //业务字段信息

    private String misTakeMsg;//错误信息

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsDate() {
        return isDate;
    }

    public void setIsDate(Integer isDate) {
        this.isDate = isDate;
    }

    public Integer getIsTime() {
        return isTime;
    }

    public void setIsTime(Integer isTime) {
        this.isTime = isTime;
    }

    public Integer getIsWeek() {
        return isWeek;
    }

    public void setIsWeek(Integer isWeek) {
        this.isWeek = isWeek;
    }

    public String getDateFormatName() {
        return dateFormatName;
    }

    public void setDateFormatName(String dateFormatName) {
        this.dateFormatName = dateFormatName;
    }

    public String getTimeFormatName() {
        return timeFormatName;
    }

    public void setTimeFormatName(String timeFormatName) {
        this.timeFormatName = timeFormatName;
    }

    public String getWeekFormatName() {
        return weekFormatName;
    }

    public void setWeekFormatName(String weekFormatName) {
        this.weekFormatName = weekFormatName;
    }

    public String getStartTimeScope() {
        return startTimeScope;
    }

    public void setStartTimeScope(String startTimeScope) {
        this.startTimeScope = startTimeScope;
    }

    public String getEndTimeScope() {
        return endTimeScope;
    }

    public void setEndTimeScope(String endTimeScope) {
        this.endTimeScope = endTimeScope;
    }

    public Integer getIsMustDt() {
        return isMustDt;
    }

    public void setIsMustDt(Integer isMustDt) {
        this.isMustDt = isMustDt;
    }

    public Integer getIsEmployDt() {
        return isEmployDt;
    }

    public void setIsEmployDt(Integer isEmployDt) {
        this.isEmployDt = isEmployDt;
    }

    public Integer getFieldIdDt() {
        return fieldIdDt;
    }

    public void setFieldIdDt(Integer fieldIdDt) {
        this.fieldIdDt = fieldIdDt;
    }

    public Integer getShowThousands() {
        return showThousands;
    }

    public void setShowThousands(Integer showThousands) {
        this.showThousands = showThousands;
    }

    public Integer getUnitTransformCode() {
        return unitTransformCode;
    }

    public void setUnitTransformCode(Integer unitTransformCode) {
        this.unitTransformCode = unitTransformCode;
    }

    public String getUnitTransformName() {
        return unitTransformName;
    }

    public void setUnitTransformName(String unitTransformName) {
        this.unitTransformName = unitTransformName;
    }

    public Integer getSaveDecimalsPlaces() {
        return saveDecimalsPlaces;
    }

    public void setSaveDecimalsPlaces(Integer saveDecimalsPlaces) {
        this.saveDecimalsPlaces = saveDecimalsPlaces;
    }

    public String getUnitShownameCn() {
        return unitShownameCn;
    }

    public void setUnitShownameCn(String unitShownameCn) {
        this.unitShownameCn = unitShownameCn;
    }

    public String getUnitShownameEn() {
        return unitShownameEn;
    }

    public void setUnitShownameEn(String unitShownameEn) {
        this.unitShownameEn = unitShownameEn;
    }

    public Integer getUnitShowlocationCode() {
        return unitShowlocationCode;
    }

    public void setUnitShowlocationCode(Integer unitShowlocationCode) {
        this.unitShowlocationCode = unitShowlocationCode;
    }

    public String getUnitShowlocationName() {
        return unitShowlocationName;
    }

    public void setUnitShowlocationName(String unitShowlocationName) {
        this.unitShowlocationName = unitShowlocationName;
    }

    public String getMoneyScopeMin() {
        return moneyScopeMin;
    }

    public void setMoneyScopeMin(String moneyScopeMin) {
        this.moneyScopeMin = moneyScopeMin;
    }

    public String getMoneyScopeMax() {
        return moneyScopeMax;
    }

    public void setMoneyScopeMax(String moneyScopeMax) {
        this.moneyScopeMax = moneyScopeMax;
    }

    public String getDefaultValueMoney() {
        return defaultValueMoney;
    }

    public void setDefaultValueMoney(String defaultValueMoney) {
        this.defaultValueMoney = defaultValueMoney;
    }

    public Integer getIsMustMoney() {
        return isMustMoney;
    }

    public void setIsMustMoney(Integer isMustMoney) {
        this.isMustMoney = isMustMoney;
    }

    public Integer getIsEmployMoney() {
        return isEmployMoney;
    }

    public void setIsEmployMoney(Integer isEmployMoney) {
        this.isEmployMoney = isEmployMoney;
    }

    public Integer getFieldIdMoney() {
        return fieldIdMoney;
    }

    public void setFieldIdMoney(Integer fieldIdMoney) {
        this.fieldIdMoney = fieldIdMoney;
    }

    public String getInputNumberWayName() {
        return inputNumberWayName;
    }

    public void setInputNumberWayName(String inputNumberWayName) {
        this.inputNumberWayName = inputNumberWayName;
    }

    public Integer getSaveDecimalsPlacesPercent() {
        return saveDecimalsPlacesPercent;
    }

    public void setSaveDecimalsPlacesPercent(Integer saveDecimalsPlacesPercent) {
        this.saveDecimalsPlacesPercent = saveDecimalsPlacesPercent;
    }

    public String getNumberScopeMin() {
        return numberScopeMin;
    }

    public void setNumberScopeMin(String numberScopeMin) {
        this.numberScopeMin = numberScopeMin;
    }

    public String getNumberScopeMax() {
        return numberScopeMax;
    }

    public void setNumberScopeMax(String numberScopeMax) {
        this.numberScopeMax = numberScopeMax;
    }

    public String getDefaultValuePercent() {
        return defaultValuePercent;
    }

    public void setDefaultValuePercent(String defaultValuePercent) {
        this.defaultValuePercent = defaultValuePercent;
    }

    public Integer getIsMustPercent() {
        return isMustPercent;
    }

    public void setIsMustPercent(Integer isMustPercent) {
        this.isMustPercent = isMustPercent;
    }

    public Integer getIsEmployPercent() {
        return isEmployPercent;
    }

    public void setIsEmployPercent(Integer isEmployPercent) {
        this.isEmployPercent = isEmployPercent;
    }

    public Integer getFieldIdPercent() {
        return fieldIdPercent;
    }

    public void setFieldIdPercent(Integer fieldIdPercent) {
        this.fieldIdPercent = fieldIdPercent;
    }

    public Integer getLineTypeCode() {
        return lineTypeCode;
    }

    public void setLineTypeCode(Integer lineTypeCode) {
        this.lineTypeCode = lineTypeCode;
    }

    public String getLineTypeName() {
        return lineTypeName;
    }

    public void setLineTypeName(String lineTypeName) {
        this.lineTypeName = lineTypeName;
    }

    public String getMaxNumberLimit() {
        return maxNumberLimit;
    }

    public String getMisTakeMsg() {
        return misTakeMsg;
    }

    public void setMisTakeMsg(String misTakeMsg) {
        this.misTakeMsg = misTakeMsg;
    }

    public String getMisTakeMsgMoney() {
        return misTakeMsgMoney;
    }

    public void setMisTakeMsgMoney(String misTakeMsgMoney) {
        this.misTakeMsgMoney = misTakeMsgMoney;
    }

    public String getMisTakeMsgValue() {
        return misTakeMsgValue;
    }

    public void setMisTakeMsgValue(String misTakeMsgValue) {
        this.misTakeMsgValue = misTakeMsgValue;
    }

    public String getMisTakeMsgPercent() {
        return misTakeMsgPercent;
    }

    public void setMisTakeMsgPercent(String misTakeMsgPercent) {
        this.misTakeMsgPercent = misTakeMsgPercent;
    }

    public String getMisTakeMsgText() {
        return misTakeMsgText;
    }

    public void setMisTakeMsgText(String misTakeMsgText) {
        this.misTakeMsgText = misTakeMsgText;
    }

    public String getMisTakeMsgDt() {
        return misTakeMsgDt;
    }

    public void setMisTakeMsgDt(String misTakeMsgDt) {
        this.misTakeMsgDt = misTakeMsgDt;
    }

    public void setMaxNumberLimit(String maxNumberLimit) {
        this.maxNumberLimit = maxNumberLimit;
    }

    public Integer getFormatValidataCode() {
        return formatValidataCode;
    }

    public void setFormatValidataCode(Integer formatValidataCode) {
        this.formatValidataCode = formatValidataCode;
    }

    public String getFormatValidataName() {
        return formatValidataName;
    }

    public void setFormatValidataName(String formatValidataName) {
        this.formatValidataName = formatValidataName;
    }

    public String getDefaultValueText() {
        return defaultValueText;
    }

    public void setDefaultValueText(String defaultValueText) {
        this.defaultValueText = defaultValueText;
    }

    public Integer getIsMustText() {
        return isMustText;
    }

    public void setIsMustText(Integer isMustText) {
        this.isMustText = isMustText;
    }

    public Integer getIsEmployText() {
        return isEmployText;
    }

    public void setIsEmployText(Integer isEmployText) {
        this.isEmployText = isEmployText;
    }

    public Integer getFieldIdText() {
        return fieldIdText;
    }

    public void setFieldIdText(Integer fieldIdText) {
        this.fieldIdText = fieldIdText;
    }

    public Integer getShowThousandsValue() {
        return showThousandsValue;
    }

    public void setShowThousandsValue(Integer showThousandsValue) {
        this.showThousandsValue = showThousandsValue;
    }

    public Integer getSaveDecimalsPlacesValue() {
        return saveDecimalsPlacesValue;
    }

    public void setSaveDecimalsPlacesValue(Integer saveDecimalsPlacesValue) {
        this.saveDecimalsPlacesValue = saveDecimalsPlacesValue;
    }

    public String getUnitShownameCnValue() {
        return unitShownameCnValue;
    }

    public void setUnitShownameCnValue(String unitShownameCnValue) {
        this.unitShownameCnValue = unitShownameCnValue;
    }

    public String getUnitShownameEnValue() {
        return unitShownameEnValue;
    }

    public void setUnitShownameEnValue(String unitShownameEnValue) {
        this.unitShownameEnValue = unitShownameEnValue;
    }

    public Integer getUnitShowlocationCodeValue() {
        return unitShowlocationCodeValue;
    }

    public void setUnitShowlocationCodeValue(Integer unitShowlocationCodeValue) {
        this.unitShowlocationCodeValue = unitShowlocationCodeValue;
    }

    public String getUnitShowlocationNameValue() {
        return unitShowlocationNameValue;
    }

    public void setUnitShowlocationNameValue(String unitShowlocationNameValue) {
        this.unitShowlocationNameValue = unitShowlocationNameValue;
    }

    public String getDefaultValueValue() {
        return defaultValueValue;
    }

    public void setDefaultValueValue(String defaultValueValue) {
        this.defaultValueValue = defaultValueValue;
    }

    public Integer getIsMustValue() {
        return isMustValue;
    }

    public void setIsMustValue(Integer isMustValue) {
        this.isMustValue = isMustValue;
    }

    public Integer getIsEmployValue() {
        return isEmployValue;
    }

    public void setIsEmployValue(Integer isEmployValue) {
        this.isEmployValue = isEmployValue;
    }

    public String getMultipleTransformValue() {
        return multipleTransformValue;
    }

    public void setMultipleTransformValue(String multipleTransformValue) {
        this.multipleTransformValue = multipleTransformValue;
    }

    public String getNumberScopeMinValue() {
        return numberScopeMinValue;
    }

    public void setNumberScopeMinValue(String numberScopeMinValue) {
        this.numberScopeMinValue = numberScopeMinValue;
    }

    public String getNumberScopeMaxValue() {
        return numberScopeMaxValue;
    }

    public void setNumberScopeMaxValue(String numberScopeMaxValue) {
        this.numberScopeMaxValue = numberScopeMaxValue;
    }

    public Integer getFieldIdValue() {
        return fieldIdValue;
    }

    public void setFieldIdValue(Integer fieldIdValue) {
        this.fieldIdValue = fieldIdValue;
    }

    public ThirdFieldInfo getThirdFieldInfo() {
        return thirdFieldInfo;
    }

    public void setThirdFieldInfo(ThirdFieldInfo thirdFieldInfo) {
        this.thirdFieldInfo = thirdFieldInfo;
    }

    public FieldtypeExtendDt getFieldtypeExtendDt() {
        return fieldtypeExtendDt;
    }

    public void setFieldtypeExtendDt(FieldtypeExtendDt fieldtypeExtendDt) {
        this.fieldtypeExtendDt = fieldtypeExtendDt;
    }

    public FieldtypeExtendMoney getFieldtypeExtendMoney() {
        return fieldtypeExtendMoney;
    }

    public void setFieldtypeExtendMoney(FieldtypeExtendMoney fieldtypeExtendMoney) {
        this.fieldtypeExtendMoney = fieldtypeExtendMoney;
    }

    public FieldtypeExtendPercent getFieldtypeExtendPercent() {
        return fieldtypeExtendPercent;
    }

    public void setFieldtypeExtendPercent(FieldtypeExtendPercent fieldtypeExtendPercent) {
        this.fieldtypeExtendPercent = fieldtypeExtendPercent;
    }

    public FieldtypeExtendText getFieldtypeExtendText() {
        return fieldtypeExtendText;
    }

    public void setFieldtypeExtendText(FieldtypeExtendText fieldtypeExtendText) {
        this.fieldtypeExtendText = fieldtypeExtendText;
    }

    public FieldtypeExtendValue getFieldtypeExtendValue() {
        return fieldtypeExtendValue;
    }

    public void setFieldtypeExtendValue(FieldtypeExtendValue fieldtypeExtendValue) {
        this.fieldtypeExtendValue = fieldtypeExtendValue;
    }

    public String getFieldTypeCode() {
        return fieldTypeCode;
    }

    public void setFieldTypeCode(String fieldTypeCode) {
        this.fieldTypeCode = fieldTypeCode;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public String getShowNameCn() {
        return showNameCn;
    }

    public void setShowNameCn(String showNameCn) {
        this.showNameCn = showNameCn;
    }

    public String getShowNameEn() {
        return showNameEn;
    }

    public void setShowNameEn(String showNameEn) {
        this.showNameEn = showNameEn;
    }

    public String getMetadataFieldName() {
        return metadataFieldName;
    }

    public void setMetadataFieldName(String metadataFieldName) {
        this.metadataFieldName = metadataFieldName;
    }

    public Integer getIsExtension() {
        return isExtension;
    }

    public void setIsExtension(Integer isExtension) {
        this.isExtension = isExtension;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getMetadataObjName() {
        return metadataObjName;
    }

    public void setMetadataObjName(String metadataObjName) {
        this.metadataObjName = metadataObjName;
    }

    public Integer getIsShowCurrency() {
        return isShowCurrency;
    }

    public void setIsShowCurrency(Integer isShowCurrency) {
        this.isShowCurrency = isShowCurrency;
    }
}
