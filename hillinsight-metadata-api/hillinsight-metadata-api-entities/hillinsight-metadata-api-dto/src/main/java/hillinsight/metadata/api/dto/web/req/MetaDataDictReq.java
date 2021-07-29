package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;

/**
 * 元数据dict点播
 *
 * @author wangchunyu
 * @date 2020/11/30
 */
public class MetaDataDictReq {


    @NotBlank(message = "字典路径不能为空！")
    private  String dictPath;

    public String getDictPath() {
        return dictPath;
    }

    public void setDictPath(String dictPath) {
        this.dictPath = dictPath;
    }
}
