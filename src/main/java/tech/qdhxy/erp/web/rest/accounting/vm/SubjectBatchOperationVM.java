package tech.qdhxy.erp.web.rest.accounting.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SubjectBatchOperationVM {
    public static final String ENABLE_ALL = "enableAll";
    public static final String DISABLE_ALL = "disableAll";
    @NotNull
    private String action;
    @NotNull
    private List<Long> ids;
}
