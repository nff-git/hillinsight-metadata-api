package hillinsight.metadata.api.portal.web.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import focus.core.ResponseResult;
import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.acs.api.sdk.RoleInfoResult;
import hillinsight.acs.api.sdk.UserInfoVo;
import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.MetaExcelExportReq;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;
import hillinsight.metadata.api.service.web.excel.MetaExcelService;
import hillinsight.metadata.api.utils.excel.ExcelExportUtil;
import hillinsight.metadata.api.utils.excel.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @ClassName MetaExcelController
 * @Description TODO 元数据excel导入  控制层
 * @Author wcy
 * @Date 2020/11/19
 * @Version 1.0
 */

@RestController
@RequestMapping(path = "/metaExcel")
@Slf4j
public class MetaExcelController {

    @Autowired
    private MetaExcelService metaExcelService;

    /**
     * 元数据字段导入
     *
     * @param file     文件
     * @param request  请求
     * @param objectId 对象id
     * @return {@link ResponseResult<String>}* @throws Exception 异常
     */
    @RequestMapping(path = "/metaExcelimport", method = RequestMethod.POST)
    public ResponseResult<MetaAndThirdImportResult> metaDateExcelimport(@RequestParam("file") MultipartFile file,
                                                                        HttpServletRequest request,
                                                                        @RequestParam(value = "objectId",required = false) Integer objectId,
                                                                        @RequestParam(value = "isSaveDb") Integer isSaveDb) throws Exception {
        File filePath = FileUtils.multipartFileToFile(file);
        ExcelReader reader = ExcelUtil.getReader(filePath);
        List<MetaFieldExcelimportVo> metaFieldExcelimportVos = reader.read(1, 2,
                MetaFieldExcelimportVo.class);
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        if(null != userInfoVo.getRoleInfos()){
            for (RoleInfoResult roleInfo : userInfoVo.getRoleInfos()) {
                log.error("用户角色列表：{"+roleInfo.getRoleName()+":"+roleInfo.getRoleCode()+"******************");
            }
        }else {
            log.error("未获取到角色列表：**************************************");
        }
        for (MetaFieldExcelimportVo metaFieldExcelimportVo : metaFieldExcelimportVos) {
            metaFieldExcelimportVo.setCreatorId("222");
            metaFieldExcelimportVo.setCreatorName("111");
            metaFieldExcelimportVo.setCreatorTime(new Date());
            metaFieldExcelimportVo.setRoleInfoResults(userInfoVo.getRoleInfos());
        }
        //获取导入错误报告模板流文件
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template/meta.xls");
        if(null == stream) throw  new BuilderException("获取错误报告模板异常！");
        return ResponseResult.success(metaExcelService.metaDateExcelimport(metaFieldExcelimportVos,objectId,stream,isSaveDb));
    }

    /**
     * 对象管理模板下载
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/metatemplateExport", method = RequestMethod.GET)
    public void metatemplateExport(HttpServletResponse response) throws IOException {
        String filePath = "template/meta.xls";
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment;fileName="+"metaImportTemplate"+".xls");
        ServletUtil.write(response,stream);
    }

    /**
     * 根据id导出对象管理Excel
     * @param metaExcelExportReq
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/metaExcelExport",method = RequestMethod.GET)
    public void metaExcelExport(MetaExcelExportReq metaExcelExportReq, HttpServletResponse response) throws IOException {
        //获取导出模板流文件
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template/meta.xls");
        if (null == stream) throw new BuilderException("获取导出模板异常！");
        ExcelExportUtil.build(stream
                , new String[]{
                        "idNum"
                        , "fieldName"
                        , "fieldShowCn"
                        , "fieldShowEn"
                        , "fieldTypeName"
                        , "fieldParaphraseCn"
                        , "fieldParaphraseEn"
                        , "dataOwnerId"
                        , "fillingExplanation"
                }, 5000);
        //根据id查询返回对象管理信息
        List<MetaFieldExcelimportVo> metaFieldList = metaExcelService.getMetaDataInfoByIds(metaExcelExportReq);
        // 列名 数据
        List<Map<String, String>> list = new ArrayList<>();
        if (null != metaFieldList ){
            for(int i = 0; i < metaFieldList.size();i++){
                Map<String, String> map = new HashMap<>();
                MetaFieldExcelimportVo obj = metaFieldList.get(i);
                map.put("idNum", String.valueOf(i+1));
                map.put("fieldName",obj.getFieldName());
                map.put("fieldShowCn",obj.getFieldShowCn());
                map.put("fieldShowEn",obj.getFieldShowEn());
                map.put("fieldTypeName",obj.getFieldTypeName());
                map.put("fieldParaphraseCn",obj.getFieldParaphraseCn());
                map.put("fieldParaphraseEn",obj.getFieldParaphraseEn());
                map.put("dataOwnerId",obj.getDataOwnerId());
                map.put("fillingExplanation",obj.getFillingExplanation());
                list.add(map);
            }
        }
        ExcelExportUtil.writeObjectNorm(list);
        File file = ExcelExportUtil.stop();
        //通过下载地址导出
        InputStream inputStream = new FileInputStream(file.getPath());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "MetaFieldList"+ DateUtil.format(new Date(), "HHmmss") + ".xls");
        ServletUtil.write(response, inputStream);
    }



    /**
     * 下载元数据导入的错误报告
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/getMetaFieldImportDis", method = RequestMethod.GET)
    public void getMetaFieldImportDis(HttpServletResponse response,@RequestParam(value = "fieldId")  String fieldId) throws IOException {
       log.error("文件id入参："+fieldId+"*******************************************");
        InputStream metaFieldImportDis = metaExcelService.getMetaFieldImportDis(fieldId);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "metaDataErrorReport" + ".xls");
        ServletUtil.write(response, metaFieldImportDis);
    }

}
