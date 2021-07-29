package hillinsight.metadata.api.acs.apisdk;

import hillinsight.metadata.api.acs.apisdk.vo.RoleInfoResult;

import java.util.List;

/**
 * @ClassName AcsRolesResponse
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/6
 * @Version 1.0
 */
public class AcsRolesResponse<T> {

    private T result;//权限系统 角色列表

    public AcsRolesResponse() {
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AcsRolesResponse{" +
                "result=" + result +
                '}';
    }
}
