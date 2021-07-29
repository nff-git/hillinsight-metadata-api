package hillinsight.metadata.api.hlink.apisdk;

public class HlinkApiResponse<TResult> {
    private String error_code;
    private String message;
    private TResult result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }
}
