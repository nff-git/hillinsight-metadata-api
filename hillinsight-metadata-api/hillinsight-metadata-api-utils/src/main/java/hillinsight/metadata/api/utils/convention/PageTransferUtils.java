package hillinsight.metadata.api.utils.convention;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import focus.core.PagedResult;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import org.apache.el.stream.Stream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName PageTransferUtils
 * @Description TODO 分页转换器
 * @Author wcy
 * @Date 2020/12/7
 * @Version 1.0
 */
public class PageTransferUtils {

    public PageTransferUtils() {
    }


    /**
     * mybatis  分页后 转换为 focus 分页结果类型
     *
     * @param pageInfo 页面信息
     * @return {@link PagedResult<T>}
     */
    public  static <T> PagedResult<T>  pageInfoTransferResult(PageInfo<T> pageInfo){
        PagedResult<T> pagedResult = new PagedResult<>();
        pagedResult.setPageIndex(pageInfo.getPageNum());
        pagedResult.setPageSize(pageInfo.getPageSize());
        pagedResult.setTotal(pageInfo.getTotal());
        pagedResult.setPageCount(pageInfo.getPages());
        pagedResult.setResult(pageInfo.getList());
        return pagedResult;

    }


    /**
     * json骆驼格式转转换
     *
     * @param o o
     * @return {@link JSONObject}
     */
    public static  JSONObject jsonCamelCasing(JSONObject o) {
        JSONObject result = new JSONObject();
        if(null != o){
            for(String str : o.keySet()){
                result.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,str),o.get(str));
            }
        }
        return result;
    }

    /**
     * json骆驼格式转转换
     *
     * @return {@link JSONObject}
     */
    public static  List<String> buildExtendTableType() {
        return  Arrays.asList("2001", "2003", "2004", "2008", "2011");//组织高级配置字段类型
    }






}
