package tech.qdhxy.erp.service.accounting.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountSetDTO {
    private Long id;
    private String code;
    // 帐套名称
    @NotNull
    private String name;
    // 帐套启用年份
    @NotNull
    private String startYear;
    // 帐套启用月份
    @NotNull
    private String startMonth;
    // 统一社会信用代码
    private String unifiedNumber;
    // 会计准则代码
    @NotNull
    private String accountingStandardCode;
    // 行业代码
    private String industryCode;
    // 增值税种类代码
    @NotNull
    private String taxTypeCode;
    // 是否开启资产模板
    private Boolean assetsModule = true;
    // 是否开启资金模块
    private Boolean cashModule = true;
    // 是否关联进销存模块
    private Boolean psiModule = true;
    // 是否审核凭证
    private Boolean voucherAudit = false;
    // 单位/数量/汇率小数
    private Integer decimalPlace = 8;
    // 纳税人名称
    private String taxPayerName;
    // 纳税人识别号
    private String taxNumber;
    // 报税地区
    private String taxProvince;
    // 电子税局密码
    private String taxPayerPassword;
    // 个税办税人员姓名
    private String taxEmpName;
    // 个税办税人员手机
    private String taxEmpPhone;
    // 个税系统密码
    private String taxEmpPassword;
    // 是否正常状态0被删除1正常
    private Integer status = 1;


}
