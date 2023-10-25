package tech.qdhxy.erp.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@Setter
public class R<T> implements Serializable {
    private boolean success;
    private int code;
    private long timestamp = System.currentTimeMillis();
    private T data;
    private String errorMsg;
    private String version = "V1.0";

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(HttpStatus.OK.value());
        r.setSuccess(true);
        r.setData(data);
        return r;
    }

    public static R<Void> success(int code) {
        R<Void> r = new R<>();
        r.setCode(code);
        r.setSuccess(true);
        return r;
    }

    public static R<Void> success() {
        R<Void> r = new R<>();
        r.setCode(HttpStatus.OK.value());
        r.setSuccess(true);
        return r;
    }
    public static R<Void> error(int code, String errorMsg) {
        R<Void> r = new R<>();
        r.setCode(code);
        r.setErrorMsg(errorMsg);
        return r;
    }
}
