package hillinsight.metadata.api.portal.demo;

import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportCopy;
import hillinsight.metadata.api.utils.excel.ExcelExportUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName exportTest
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/2
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/testExcel")
public class exportController {


    /**
     * 测试导出错误报告
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public void metatemplateExport(HttpServletResponse response) throws IOException {
        String filePath = "template/third.xls";
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        ExcelExportUtil.build(stream
                , new String[]{
                        "idNum"
                        , "fieldName"
                        , "fieldTypeName"
                        , "showNameCn"
                        , "showNameEn"
                        , "metadataObjName"
                        , "metadataFieldName"
                        , "isExtension"
                        , "extensionName"
                        , "misTakeMsg"
                }, 5000);
        //构建数据
        List<ThirdFieldExcelimportCopy> thirdFieldExcelimportCopies = new ArrayList<>();
        ThirdFieldExcelimportCopy thirdFieldExcelimportCopy = new ThirdFieldExcelimportCopy();
        thirdFieldExcelimportCopy.setIdNum("1");
        thirdFieldExcelimportCopy.setFieldName("Deal");
//        thirdFieldExcelimportCopy.setFieldTypeName("fefef");
        thirdFieldExcelimportCopy.setShowNameCn("dwdw");
        thirdFieldExcelimportCopy.setShowNameEn("fefef");
        thirdFieldExcelimportCopy.setMetadataObjName("fefergrg");
        thirdFieldExcelimportCopy.setMetadataFieldName("rgrghrugr");
        thirdFieldExcelimportCopy.setIsExtension(1);
        thirdFieldExcelimportCopy.setExtensionName("t_excetion_1");
        thirdFieldExcelimportCopy.setMisTakeMsg("fieldTypeName 为空值;");
        thirdFieldExcelimportCopies.add(thirdFieldExcelimportCopy);

        MetaAndThirdImportResult metaFieldImportResult = new MetaAndThirdImportResult();
        Map<String, List<Map<String, Object>>> failureMap = new HashMap<>();
        List<Map<String,Object>> failureList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("1","fieldTypeName 为空值;");
        failureList.add(map);
        metaFieldImportResult.setExSuccess(true);
        metaFieldImportResult.setSuccessCount(2);
        metaFieldImportResult.setTotalCount(3);
        failureMap.put("meta",failureList);
        metaFieldImportResult.setFailureList(failureMap);
        ExcelExportUtil.writeObject(thirdFieldExcelimportCopies,metaFieldImportResult,2);
        System.out.println("---------------------------------------------");
        File stop = ExcelExportUtil.stop();
        System.out.println("下载地址："+ stop.getPath());

    }
}
