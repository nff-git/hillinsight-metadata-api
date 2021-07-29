package hillinsight.metadata.api.utils.convention;


/**
 * 常量类
 */
public class Constant {

    /**
     * 基本数字常量
     */
    public static final int METADATA_ZERO = 0;
    public static final int METADATA_ONE = 1;
    public static final int METADATA_TWO = 2;
    public static final int METADATA_THREE = 3;
    public static final String EXTENSION_INFO = "extension_1,extension_2,extension_3,extension_4,extension_5," +
            "extension_6,extension_7," +
            "extension_8,extension_9,extension_10";


    /**
     * 基本用户字符变量
     */
    public static final String METADATA_SUCCESS_STRING = "操作成功！";
    public static final String PPAGCONFIG_IMPROT_STATUS_NEW = "NEW";
    public static final String PPAGCONFIG_IMPROT_STATUS_UPDATE = "UPDATE";
    public static final String PPAGCONFIG_IMPROT_STATUS_FAIL = "FAIL";


    /**
     * redis 缓存 key值
     */

    public static final String REDIS_DICT_PARENT_ALL = "parent_dict_list";


    /**
     * 字典Path区
     */

    //字段类型
    public static final String DICT_FIELD_TYPE = "/dict_field_type";


    /**
     * 元数据对象分类
     */
    public static final Integer META_OBJECT_TYPE = 1; //元数据对象

    public static final Integer THIRD_OBJECT_TYPE = 2;//业务对象


    /**
     * 高级配置字段code
     */
    //文本
    public static final String FILEID_TYPE_CODE_TEXT = "2001";
    //数值
    public static final String FILEID_TYPE_CODE_VALUE = "2003";
    //金额
    public static final String FILEID_TYPE_CODE_MONEY = "2004";
    //日期/时间
    public static final String FILEID_TYPE_CODE_DT = "2008";
    //百分比
    public static final String FILEID_TYPE_CODE_PERCENT = "2011";


    /**
     * 高级配置字段类型字典path
     */

    //单位转换
    public static final String UNIT_TRANSFORM = "/unit_transform";
    //单位显示位置
    public static final String UNIT_SHOW_LOCATIONINPUT = "/unit_show_location";
    //输入数值方式
    public static final String INPUT_NUMBER_WAY = "/input_number_way";
    //行类型
    public static final String LINE_TYPE = "/line_type";
    //格式校验
    public static final String FORMAT_VALIDATA = "/format_validata";
    //日期格式
    public static final String DATETIMEFORMAT = "/datetime_format";
    //时间格式
    public static final String TIMEFORMAT = "/time_format";
    //星期格式
    public static final String WEEKFORMAT = "/week_format";


    /**
     * 高级配置字段类型字典code
     */
    //千元
    public static final Integer DICT_CODE_THOUSAND = 5001;
    //万元
    public static final Integer DICT_CODE_TEN_THOUSAND = 5002;
    //亿元
    public static final Integer DICT_CODE_100_MILLION = 5003;
    //百万
    public static final Integer DICT_CODE_MILLION = 5004;
    //前缀
    public static final Integer DICT_CODE_PREFIX = 6001;
    //后缀
    public static final Integer DICT_CODE_SUFFIX = 6002;
    //小数方式
    public static final Integer DICT_CODE_DECIMALS = 7001;
    //百分数方式
    public static final Integer DICT_CODE_PERCENT = 7002;
    //单行
    public static final Integer DICT_CODE_UNILINE = 8001;
    //多行
    public static final Integer DICT_CODE_MULTILINE = 8002;
    //邮箱
    public static final Integer DICT_CODE_MAILBOX = 9001;
    //大陆手机号
    public static final Integer DICT_CODE_PHONENUMBER = 9002;
    //身份证
    public static final Integer DICT_CODE_IDENTITYCARD = 9003;

    // YYYY/MM/DD
    public static final Integer DICT_CODE_DATE_FORMAT_1 = 11001;
    // YY/MM/DD
    public static final Integer DICT_CODE_DATE_FORMAT_2 = 11002;
    // MM/DD
    public static final Integer DICT_CODE_DATE_FORMAT_3 = 11003;
    // YYYY-MM-DD
    public static final Integer DICT_CODE_DATE_FORMAT_4 = 11004;
    // YY-MM-DD
    public static final Integer DICT_CODE_DATE_FORMAT_5 = 11005;
    // MM-DD
    public static final Integer DICT_CODE_DATE_FORMAT_6 = 11006;
    // MM/DD/YYYY
    public static final Integer DICT_CODE_DATE_FORMAT_7 = 11007;
    // MM/DD/YY
    public static final Integer DICT_CODE_DATE_FORMAT_8 = 11008;
    // YYYY年MM月DD日
    public static final Integer DICT_CODE_DATE_FORMAT_9 = 11009;
    // YY年MM月DD日
    public static final Integer DICT_CODE_DATE_FORMAT_10 = 11010;
    // MM月DD日
    public static final Integer DICT_CODE_DATE_FORMAT_11 = 11011;

    // HH:mm:ss
    public static final Integer DICT_CODE_TIME_FORMAT_1 = 12001;
    //HH:mm
    public static final Integer DICT_CODE_TIME_FORMAT_2 = 12002;
    //HH时mm分ss秒
    public static final Integer DICT_CODE_TIME_FORMAT_3 = 12003;
    //HH时mm分
    public static final Integer DICT_CODE_TIME_FORMAT_4 = 12004;

    //Mon
    public static final Integer DICT_CODE_WEEK_FORMAT_1 = 13001;
    //Monday
    public static final Integer DICT_CODE_WEEK_FORMAT_2 = 13002;
    //星期一
    public static final Integer DICT_CODE_WEEK_FORMAT_3 = 13003;
    //周一
    public static final Integer DICT_CODE_WEEK_FORMAT_4 = 13004;
    //礼拜一
    public static final Integer DICT_CODE_WEEK_FORMAT_5 = 13005;

}
