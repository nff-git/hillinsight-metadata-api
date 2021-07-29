package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import focus.mybatis.extensions.annotations.Id;
import focus.mybatis.extensions.annotations.Table;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 元数据对象实体类
 *
 * @author wcy
 */
@TableName("t_metadata_object_info")
@Table(tableName = "t_metadata_object_info")
public class MetaDataObjectInfo extends BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;
    @NotBlank(message = "对象显示名不能为空")
    private String showName;//对象显示名
    @NotBlank(message = "对象名不能为空")
    private String objectName;//对象名
//    @NotBlank(message = "描述不能为空")
    private String describe;//描述
    private Integer status;//1启用 0禁用
//    @NotBlank(message = "父级id不能为空")
    private Integer parentId;//父级id

    /**
     * 非数据库字段
     */
    private String parentObjName;

    private String parentObjShowName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentObjName() {
        return parentObjName;
    }

    public void setParentObjName(String parentObjName) {
        this.parentObjName = parentObjName;
    }

    public String getParentObjShowName() {
        return parentObjShowName;
    }

    public void setParentObjShowName(String parentObjShowName) {
        this.parentObjShowName = parentObjShowName;
    }

    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        super.setCreatorId(userCode);
        super.setCreatorName(userName);
        super.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }


}
