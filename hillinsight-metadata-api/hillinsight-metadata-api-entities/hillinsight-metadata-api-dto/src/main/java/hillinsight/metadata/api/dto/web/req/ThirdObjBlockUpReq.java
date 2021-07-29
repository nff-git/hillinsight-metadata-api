package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.ThirdObjectInfo;

import javax.validation.Valid;

/**
 * @ClassName ThirdObjBlockUpReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/11
 * @Version 1.0
 */
public class ThirdObjBlockUpReq {

    @Valid
    private ThirdObjectInfo thirdObjectInfo;

    public ThirdObjectInfo getThirdObjectInfo() {
        return thirdObjectInfo;
    }

    public void setThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {
        this.thirdObjectInfo = thirdObjectInfo;
    }
}
