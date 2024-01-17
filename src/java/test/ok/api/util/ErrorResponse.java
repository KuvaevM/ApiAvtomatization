package ok.api.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty("error_code")
    public int errorCode;

    @JsonProperty("error_msg")
    public String errorMsg;

    @JsonProperty("error_data")
    public Object errorData;
}
