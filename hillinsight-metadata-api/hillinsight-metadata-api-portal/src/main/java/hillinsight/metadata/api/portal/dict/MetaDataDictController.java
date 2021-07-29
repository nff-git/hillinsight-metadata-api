package hillinsight.metadata.api.portal.dict;


import focus.core.ResponseResult;
import hillinsight.metadata.api.dto.web.req.MetaDataDictReq;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * MetaDataDictController 元数据字典控制层
 *
 * @author wangchunyu
 * @date 2020/12/10
 */
@RestController
@RequestMapping("/dict")
@Validated
public class MetaDataDictController {

    @Autowired
    private MetaDataDictService metaDataDictService;


    /**
     * 查询字典父级列表
     * @return 返回字典列表信息
     */
    @RequestMapping(path = "/getParentDictList", method = RequestMethod.GET)
    public ResponseResult<List<MetadataDict>> getParentDictList() {
        return ResponseResult.success(metaDataDictService.getParentDictList());
    }

    /**
     * 根据字典路径查询字典列表
     * @return 返回字典列表信息
     */
    @RequestMapping(path = "/getDictListByPath", method = RequestMethod.POST)
    public ResponseResult<List<MetadataDict>> getDictListByPath(@RequestBody MetaDataDictReq metaDataDictReq) {
        return ResponseResult.success(metaDataDictService.getDictListByPath(metaDataDictReq));
    }


    /**
     * 根据字典路径查询字段类型高级配置字典列表
     *
     * @param metaDataDictReq 元数据dict点播
     * @return {@link ResponseResult<List<MetadataDict>>}
     */
    @RequestMapping(path = "/getFTExpertConfigDictListByPath", method = RequestMethod.POST)
    public ResponseResult<Map<String,List<MetadataDict>>> getFTExpertConfigDictListByPath(@RequestBody MetaDataDictReq metaDataDictReq) {
        return ResponseResult.success(metaDataDictService.getFTExpertConfigDictListByPath(metaDataDictReq));
    }

}
