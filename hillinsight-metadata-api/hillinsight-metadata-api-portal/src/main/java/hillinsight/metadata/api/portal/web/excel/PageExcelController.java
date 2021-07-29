package hillinsight.metadata.api.portal.web.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONArray;
import focus.core.ResponseResult;
import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.metadata.api.dto.web.req.PageConfigImportReq;
import hillinsight.metadata.api.dto.web.PageConfigImportResult;
import hillinsight.metadata.api.service.web.excel.PageExcelService;
import hillinsight.metadata.api.web.MetadataPageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName PageExcelController
 * @Description TODO 页面配置 excel 控制层
 * @Author wcy
 * @Date 2020/12/1
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/api/pageExcel")
@Slf4j
public class PageExcelController {

    @Autowired
    private PageExcelService pageExcelService;


    /**
     * 页面配置导入
     *
     * @param file       文件
     * @param request    请求
     * @param groupId    组id
     * @param sourceId   源id
     * @param sourceName 源名称
     * @return {@link ResponseResult<List<PageConfigImportResult>>}* @throws Exception 异常
     */
    @RequestMapping(path = "/pageExcelimport", method = RequestMethod.POST)
    public ResponseResult<List<PageConfigImportResult>> pageConfigImport(@RequestParam("file") MultipartFile file,
                                                                         HttpServletRequest request,
                                                                         @RequestParam(value = "groupId") Integer groupId,
                                                                         @RequestParam(value = "sourceId") String sourceId,
                                                                         @RequestParam(value = "sourceName") String sourceName) throws Exception {
        List<PageConfigImportResult> results = new ArrayList<PageConfigImportResult>();
        //获取文件名称
        String filename = file.getOriginalFilename();
        //截取后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        //将MultipartFile  转换成file类型
        File file1 = hillinsight.metadata.api.utils.excel.FileUtils.multipartFileToFile(file);
//        try {
            //将文件内容转换成 json字符串
            String fileToString = FileUtils.readFileToString(file1, "UTF-8");
            //判断是否为json文件
            if (".json".equals(suffixName)) {
                //文件转换成 实体对象
                List<MetadataPageInfo> metadataPageInfos = JSONArray.parseArray(fileToString, MetadataPageInfo.class);
                ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
                for (MetadataPageInfo metadataPageInfo : metadataPageInfos) {
                    metadataPageInfo.initializedUserInfo(apiAcsPermissionResult.getUserInfoVo().getUserCode(),
                            apiAcsPermissionResult.getUserInfoVo().getUserName());
                }
                results = pageExcelService.importPageInfo(new PageConfigImportReq(metadataPageInfos, groupId, sourceId, sourceName));
            } else {
                throw new BuilderException("文件格式不正确，请传入.json文件");
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            //抛出异常
//            throw new BuilderException("文件转换失败！请联系管理员");
//        }
        return ResponseResult.success(results);
    }


    /**
     * 根据id导出页面配置json格式
     *
     * @param ids id
     */
    @RequestMapping(path = "/pageExcelExport", method = RequestMethod.GET)
    public void pageExcelExport(@RequestParam(value = "ids") @NotBlank String ids, HttpServletResponse response) {
        List<MetadataPageInfo> metadataPageInfos = pageExcelService.getPageConfigInfoByIds(ids);
        JSONArray toJSON = (JSONArray) JSONArray.toJSON(metadataPageInfos);
        String pageInfoJsonStr = toJSON.toJSONString();
        log.info("页面配置解析的json字符串：&&" + pageInfoJsonStr + "&&********************************************");
        MetadataPageInfo metadataPageInfo = metadataPageInfos.get(0);
        //设置文件名
        String fielName = metadataPageInfo.getSourceName() + metadataPageInfo.getSourceId() + DateUtil.format(new Date(), "HHmmss");
        response.setHeader("Content-Disposition", "attachment;filename=" + fielName + ".json");
        ServletUtil.write(response, pageInfoJsonStr, "application/octet-stream; charset=utf-8");
    }
}
