package tech.qdhxy.erp.web.rest.accounting.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSetPageVM {
    private Long id;
    private String code;
    // 帐套名称
    private String name;
    // 帐套启用年份
    private String startYear;
    // 帐套启用月份
    private String startMonth;
    // 会计准则
    private String accountingStandardCode;
    private String accountingStandard;
    // 是否开启资产模板
    private Boolean assetsModule;
    // 是否开启资金模块
    private Boolean cashModule;
    // 是否审核凭证
    private Boolean voucherAudit;
    // 是否正常状态0被删除1正常
    private Integer status;

    // 当前账户在帐套的权限
    private String accountSetPermission;

    private String startYearMonth;
    private String currentYearMonth;

    public String getStartYearMonth() {
        return String.join("", this.startYear, "年", this.startMonth, "月");
    }
}
