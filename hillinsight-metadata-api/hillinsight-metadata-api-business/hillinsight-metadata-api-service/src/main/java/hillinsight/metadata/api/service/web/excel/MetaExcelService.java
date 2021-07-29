package hillinsight.metadata.api.service.web.excel;

import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.MetaExcelExportReq;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;

import java.io.InputStream;
import java.util.List;

/**
 * @ClassName MetaExcelService
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/19
 * @Version 1.0
 */
public interface MetaExcelService {

    MetaAndThirdImportResult metaDateExcelimport(List<MetaFieldExcelimportVo> metaFieldExcelimportVos,
                                                 Integer objectId, InputStream stream,Integer isSaveDb);

    List<MetaFieldExcelimportVo> getMetaDataInfoByIds(MetaExcelExportReq metaExcelExportReq);

    InputStream getMetaFieldImportDis(String fieldId);
}
