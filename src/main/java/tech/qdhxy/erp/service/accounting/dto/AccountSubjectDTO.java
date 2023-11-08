package tech.qdhxy.erp.service.accounting.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AccountSubjectDTO {
    private Long id;
    // 帐套code
    private String accountSetCode;
    // 会计分类
    @NotNull
    private String category;
    // 层级
    @NotNull
    private Integer level;
    // 科目代码
    @NotNull
    private String code;
    // 科目名称
    @NotNull
    private String name;
    // 助记码
    private String fastCode;
    // 余额方向 1借 2贷
    @NotNull
    private Integer direction;
    // 上级ID
    @NotNull
    private Long pid = 0L;
    // 状态 0停用 1启用
    private Integer status = 1;
    // 是否系统预设
    private Boolean def = false;
}
