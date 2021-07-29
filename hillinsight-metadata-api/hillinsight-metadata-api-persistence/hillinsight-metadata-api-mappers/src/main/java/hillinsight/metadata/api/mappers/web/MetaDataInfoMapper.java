package hillinsight.metadata.api.mappers.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hillinsight.metadata.api.dto.web.MetaFieldListResult;
import hillinsight.metadata.api.mappers.web.provider.MetaDataInfoProvider;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDataInfoMapper extends BaseMapper<Object> {

    //添加元数据对象信息
    @InsertProvider(type = MetaDataInfoProvider.class, method = "addObjectInfo")
    void addObjectInfo(MetaDataObjectInfo metaDataObjectInfo);

    //修改元数据对象信息
    @UpdateProvider(type = MetaDataInfoProvider.class, method = "updateObjectInfo")
    void updateObjectInfo(MetaDataObjectInfo metaDataObjectInfo);

    //添加元数据字段信息
    @InsertProvider(type = MetaDataInfoProvider.class, method = "addFieldInfo")
    void addFieldInfo(MetaDataFieldInfo metaDataFieldInfo);

    //修改元数据字段信息
    @UpdateProvider(type = MetaDataInfoProvider.class, method = "updateFieldInfo")
    void updateFieldInfo(MetaDataFieldInfo metaDataFieldInfo);

    //模糊查询对象列表
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getObjectList")
    List<MetaDataObjectInfo> getObjectList(String objectName);

    //根据对象id查询字段列表
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getFieldListByObjectId")
    List<MetaDataFieldInfo> getFieldListByObjectId(Integer objectId,String criteria);

    //查询字段详情
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getFieldetailById")
    MetaDataFieldInfo getFieldetailById(Integer fieldId);

    //字段停用
    @SelectProvider(type = MetaDataInfoProvider.class, method = "fieldBlockUp")
    void fieldBlockUp(MetaDataFieldInfo metaDataFieldInfo);

    //查询元数据字段列表（新增业务字段映射元数据字段时使用）
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaFieldList")
    List<MetaFieldListResult> getMetaFieldList(String sourceId,Integer thirdObjId,Integer metaDataObjId);

    //根据对象名查询元数据对象信息
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getmetaDataObjInfoByName")
    MetaDataObjectInfo getmetaDataObjInfoByName(String objectName);

    //根据元数据字段名查询字段信息
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaFieldByName")
    MetaDataFieldInfo getMetaFieldByName(String fieldName, Integer objectId);

    //获取元数据对象列表 （业务对象新增时绑定元数据对象使用）
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaObjListForThirdObj")
    List<MetaDataObjectInfo> getMetaObjListForThirdObj(String sourceId);

    //获取元数据父对象列表（新增元数据对象时使用）
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaParentList")
    List<MetaDataObjectInfo> getMetaParentList();

    //对象状态启用停用
    @UpdateProvider(type = MetaDataInfoProvider.class, method = "ObjectUseOrBlockUp")
    void ObjectUseOrBlockUp(MetaDataObjectInfo metaDataObjectInfo);

    //根据对象id获取元数据对象信息
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getObjectById")
    MetaDataObjectInfo getObjectById(Integer objectId);

    //获取元数据对象列表   业务字段新增绑定元数据字段时使用
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaObjListForThirdField")
    List<MetaDataObjectInfo> getMetaObjListForThirdField(String sourceId,Integer thirdObjId);

    //根据id查询对象管理信息
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaDataInfoById")
    MetaFieldExcelimportVo getMetaDataInfoById(Integer id);

    //查询对象管理信息
    @SelectProvider(type = MetaDataInfoProvider.class, method = "getMetaDataInfo")
    List<MetaFieldExcelimportVo> getMetaDataInfo(Integer metaDataObjId);

    //根据id删除字段信息
    @DeleteProvider(type = MetaDataInfoProvider.class, method = "deleteFieldById")
    void deleteFieldById(Integer fieldId);
}
