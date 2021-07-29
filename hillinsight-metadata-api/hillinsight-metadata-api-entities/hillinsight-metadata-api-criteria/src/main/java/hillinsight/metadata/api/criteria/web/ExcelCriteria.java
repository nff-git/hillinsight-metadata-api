package hillinsight.metadata.api.criteria.web;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import focus.mybatis.extensions.annotations.Id;
import focus.mybatis.extensions.annotations.Table;

import java.util.Date;

@Table(tableName = "t_metadata_page")
@TableName("t_metadata_page")
public class ExcelCriteria {

    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    @ExcelProperty(index = 0,value = "页面标题")
    private String pageTitle;

    @ExcelProperty(index = 1,value = "page_key")
    private String pageKey;

    @ExcelProperty(index = 2,value = "页面分组")
    private String pageGroup;

    @ExcelProperty(index = 3,value = "页面描述")
    private String pageExplanation;

    @ExcelProperty(index = 4,value = "创建人")
    private String creatorName;

    @ExcelProperty(index = 5,value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getPageExplanation() {
        return pageExplanation;
    }

    public void setPageExplanation(String pageExplanation) {
        this.pageExplanation = pageExplanation;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ExcelCriteria{" +
                "id=" + id +
                ", pageTitle='" + pageTitle + '\'' +
                ", pageKey='" + pageKey + '\'' +
                ", pageGroup='" + pageGroup + '\'' +
                ", pageExplanation='" + pageExplanation + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
