package tech.qdhxy.erp.common.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PageQuery {
    @NotNull
    @Min(value = 1, message = "最小值为1")
    private long current;
    @NotNull
    @Min(value = 1,message = "最小值为1")
    private long size;
}
