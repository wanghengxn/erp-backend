package tech.qdhxy.erp.web.rest.sso.vm;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public enum LoginType {
    MOBILE("手机号登录"),
    EMAIL("邮箱登录"),
    ;
    private final String desc;
    LoginType(String desc) {
        this.desc = desc;
    }

    public static LoginType ofTypeName(String typeName) {
        if(StringUtils.hasText(typeName)) {
            if(MOBILE.name().equals(typeName)) {
                return MOBILE;
            } else if(EMAIL.name().equals(typeName)) {
                return EMAIL;
            }
        }
        return null;
    }
}
