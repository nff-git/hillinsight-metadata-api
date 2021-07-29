package hillinsight.metadata.api.mappers.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hillinsight.metadata.api.dto.web.ThirdFieldResult;
import hillinsight.metadata.api.mappers.web.provider.ThirdInfoProvider;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThirdInfoMapper extends BaseMapper<Object> {

    //添加业务对象信息
    @InsertProvider(type = ThirdInfoProvider.class, method = "addThirdObjectInfo")
    void addThirdObjectInfo(ThirdObjectInfo thirdObjectInfo);

    //修改业务对象信息
    @UpdateProvider(type = ThirdInfoProvider.class, method = "updateThirdObjectInfo")
    void updateThirdObjectInfo(ThirdObjectInfo thirdObjectInfo);

    //根据id获取业务对象信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjectInfoById")
    ThirdObjectInfo getThirdObjectInfoById(Integer thirdObjectId);

    //添加业务字段信息
    @InsertProvider(type = ThirdInfoProvider.class, method = "addThirdFieldInfo")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addThirdFieldInfo(ThirdFieldInfo thirdFieldInfo);

    //修改业务字段信息
    @UpdateProvider(type = ThirdInfoProvider.class, method = "updateThirdFieldInfo")
    void updateThirdFieldInfo(ThirdFieldInfo thirdFieldInfo);

    //查询字段列表
    @SelectProvider(type = ThirdInfoProvider.class, method = "getFieldListByObjId")
    List<ThirdFieldInfo> getFieldListByObjId(Integer thirdObjectId,String criteria);

    //查询业务字段详情
    @SelectProvider(type = ThirdInfoProvider.class, method = "getFieldDetailById")
    ThirdFieldInfo getFieldDetailById(Integer thirdFieldId);

    //业务字段停用
    @UpdateProvider(type = ThirdInfoProvider.class, method = "thirdfieldBlockUp")
    void thirdfieldBlockUp(ThirdFieldInfo thirdFieldInfo);

    //查询业务字段使用系统集合
    @SelectProvider(type = ThirdInfoProvider.class, method = "getFieldSysListByMetaFieldId")
    ThirdFieldResult getFieldSysListByMetaFieldId(Integer id);

    //根据业务对象名查询业务对象信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjInfoByNameAndSourceId")
    ThirdObjectInfo getThirdObjInfoByNameAndSourceId(String objectName,String sourceId);


    //根据业务字段名查询字段信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByName")
    ThirdFieldInfo getThirdFieldByName(String fieldName, Integer thirdObjectId);

    //根据业务对象名称查询业务对象列表
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjectList")
    List<ThirdObjectInfo> getThirdObjectList(String sourceId,String criteria);

    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByObjId")
    List<ThirdFieldInfo> getThirdFieldByObjId(Integer thirdObjectId);

    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByObjIdAndExt")
    ThirdFieldInfo getThirdFieldByObjIdAndExt(Integer thirdObjId, String extensionName);

    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByIdAndObjId")
    ThirdFieldInfo getThirdFieldByIdAndObjId(Integer thirdFieldjId, Integer thirdObjId);

    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdParentObjList")
    List<ThirdObjectInfo> getThirdParentObjList(String sourceId);


    //业务对象启用停用
    @UpdateProvider(type = ThirdInfoProvider.class, method = "thirdObjUseOrBlockUp")
    void thirdObjUseOrBlockUp(ThirdObjectInfo thirdObjectInfo);

    //查询业务对象根据对象id和名称
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjectInfoByIdAndName")
    ThirdObjectInfo getThirdObjectInfoByIdAndName(Integer thirdObjectId, String objectName);

    //根据业务对象id和元数据字段id获取业务字段信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByThirdObjIdAndMetaFeildId")
    ThirdFieldInfo getThirdFieldByThirdObjIdAndMetaFeildId(Integer thirdObjId, Integer id);

    //获取业务对象信息根据 对象名称和系统来源
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjectInfoByNameAndSourceId")
    ThirdObjectInfo getThirdObjectInfoByNameAndSourceId(String thirdObjName, String sourceId);

    //根据业务对象id和字段名称获取业务字段信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldByNameAndObjId")
    ThirdFieldInfo getThirdFieldByNameAndObjId(String thirdFieldName, Integer thirdObjId);


    //跟据业务字段名称和系统来源获取业务字段信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getFieldInfoByNameAndSourceIdAndObjId")
    ThirdFieldInfo getFieldInfoByNameAndSourceIdAndObjId(String thirdFieldName, String sourceId,Integer thirdObjId);

    //删除字段
    @DeleteProvider(type = ThirdInfoProvider.class, method = "deleteFieldById")
    void deleteFieldById(Integer id);

    //根据id导出业务对象Excel
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdDataInfoById")
    ThirdFieldExcelimportVo getThirdDataInfoById(Integer id);

    //导出业务对象Excel
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdDataInfoByUsercode")
    List<ThirdFieldExcelimportVo> getThirdDataInfoByUsercode(Integer thirdObjectId,String sourceId);

    //根据元数据字段id获取业务字段信息
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdFieldInfoByMetaFieldId")
    List<ThirdFieldInfo> getThirdFieldInfoByMetaFieldId(Integer metaDataFieldId);

    //根据系统来源查询业务对象集合
    @SelectProvider(type = ThirdInfoProvider.class, method = "getThirdObjectInfoBySourceId")
    List<ThirdObjectInfo> getThirdObjectInfoBySourceId(String sourceId);
}