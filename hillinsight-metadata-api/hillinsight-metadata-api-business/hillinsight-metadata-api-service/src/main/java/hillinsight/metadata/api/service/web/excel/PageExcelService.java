package hillinsight.metadata.api.service.web.excel;

import hillinsight.metadata.api.dto.web.req.PageConfigImportReq;
import hillinsight.metadata.api.dto.web.PageConfigImportResult;
import hillinsight.metadata.api.web.MetadataPageInfo;

import java.util.List;

/**
 * @ClassName PageExcelService
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/2
 * @Version 1.0
 */
public interface PageExcelService {


    List<MetadataPageInfo> getPageConfigInfoByIds(String ids);

    List<PageConfigImportResult> importPageInfo(PageConfigImportReq pageConfigImportReq);
}
