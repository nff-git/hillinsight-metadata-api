package hillinsight.metadata.api.models.enums;

/**
 * @ClassName StatusEnum
 * @Description TODO 使用状态枚举
 * @Author wcy
 * @Date 2020/11/25
 * @Version 1.0
 */
public enum  StatusEnum {

    EMPLOY("1", "启用"),
    BLOCKUP("0", "停用");

    private String key;
    private String value;

    StatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 获取value
     *
     * @param key 关键
     * @return {@link String}
     */
    public static String getValue(String key) {
        if (null != key) {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (key.equals(statusEnum.getKey())) {
                    return statusEnum.getValue();
                }
            }
        }
        return key;
    }
}
