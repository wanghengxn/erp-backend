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
    private String category;
    // 层级
    private Integer level;
    // 科目代码
    private String code;
    // 科目名称
    private String name;
    // 助记码
    private String fastCode;
    // 余额方向 1借 2贷
    private Integer direction;
    // 上级ID
    private Long pid;
    // 状态 0停用 1启用
    private Integer status = 1;
}
