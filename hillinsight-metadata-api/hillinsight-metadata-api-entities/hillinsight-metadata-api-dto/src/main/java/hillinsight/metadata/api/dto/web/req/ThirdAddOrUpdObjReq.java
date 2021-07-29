package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.ThirdObjectInfo;

import javax.validation.Valid;

/**
 * 请求添加或修改业务对象入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class ThirdAddOrUpdObjReq {

    @Valid
    private ThirdObjectInfo  thirdObjectInfo;

    public ThirdObjectInfo getThirdObjectInfo() {
        return thirdObjectInfo;
    }

    public void setThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {
        this.thirdObjectInfo = thirdObjectInfo;
    }
}
