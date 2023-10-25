package tech.qdhxy.erp.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
