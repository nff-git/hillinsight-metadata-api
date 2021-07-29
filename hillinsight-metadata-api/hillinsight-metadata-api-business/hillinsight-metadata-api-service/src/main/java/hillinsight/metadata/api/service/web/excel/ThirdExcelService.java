package hillinsight.metadata.api.service.web.excel;

import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.ThirdObjectExcelExportReq;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;

import java.io.InputStream;
import java.util.List;

public interface ThirdExcelService {


    MetaAndThirdImportResult thirdExcelimport(List<List<ThirdFieldExcelimportVo>> thirdFieldExcelimportVos,
                                              Integer thirdObjId, InputStream stream,Integer isSaveDb);

    List<ThirdFieldExcelimportVo> getThirdDataInfoByIds(ThirdObjectExcelExportReq thirdObjectExcelExportReq);

    InputStream getThirdFieldImportDis(String fieldId);

}
