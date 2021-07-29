package hillinsight.metadata.api.models;

/**
 * 元数据字典实体类
 */
public class MetadataDict {

    private Integer id;

    private String code;//字典值code

    private String name;//字典翻译名

    private Integer parentId;//父级id

    private String dictPath;//字典路径

    private String remark;//备注

    private int orderNum;//排序


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDictPath() {
        return dictPath;
    }

    public void setDictPath(String dictPath) {
        this.dictPath = dictPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "MetadataDict{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", dictPath='" + dictPath + '\'' +
                ", remark='" + remark + '\'' +
                ", orderNum=" + orderNum +
                '}';
    }
}
