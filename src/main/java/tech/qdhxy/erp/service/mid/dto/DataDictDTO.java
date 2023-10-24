package tech.qdhxy.erp.service.mid.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DataDictDTO {
    private Long id;
    @NotNull
    private String groupKey;
    @NotNull
    private String groupName;
    @NotNull
    private String itemKey;
    @NotNull
    private String itemValue;

    private Boolean status = true;
}
