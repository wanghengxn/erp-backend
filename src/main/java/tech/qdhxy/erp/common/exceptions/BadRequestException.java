package tech.qdhxy.erp.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private String msg;

    public BadRequestException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
