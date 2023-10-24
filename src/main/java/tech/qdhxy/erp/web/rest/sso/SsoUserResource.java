package tech.qdhxy.erp.web.rest.sso;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qdhxy.erp.common.exceptions.BusinessException;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.service.sso.SsoUserService;
import tech.qdhxy.erp.web.rest.sso.query.SsoUserQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sso")
@Validated
public class SsoUserResource {
    private final SsoUserService ssoUserService;

    @GetMapping("/users1")
    public void user1(@NotNull(message = "不能为空") String message) {
        throw new BusinessException("服务不可用");
    }

    @GetMapping("/users")
    public MyPage<SsoUser> getPage(@Valid SsoUserQuery pageQuery) {
        MyPage<SsoUser> myPage = new MyPage<>();
        SsoUser ssoUser = new SsoUser();
        ssoUser.setId(1L);
        ssoUser.setActivated(true);
        ssoUser.setName("zhangsan");
        ssoUser.setMobile("11111");
        ssoUser.setEmail("111@fa.com");
        ssoUser.setRealName("1111");
        myPage.setData(Arrays.asList(ssoUser));
        return myPage;
    }

    // 获取当前登录用户信息
    @GetMapping("/account/current")
    public SsoUser getCurrentLoginUser() {
        return ssoUserService.getCurrentLoginUser();
    }
}
