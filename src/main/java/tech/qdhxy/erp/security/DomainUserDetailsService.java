package tech.qdhxy.erp.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.service.sso.SsoUserService;
import tech.qdhxy.erp.web.rest.sso.vm.LoginType;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Component
public class DomainUserDetailsService implements UserDetailsService {
    private final SsoUserService ssoUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] arr = username.split(":");
        LoginType loginType = LoginType.ofTypeName(arr[0]);
        Optional<SsoUser> ssoUserOptional = Optional.empty();
        if(LoginType.MOBILE.equals(loginType)) {
            ssoUserOptional = this.ssoUserService.getOneByMobile(arr[1]);
        } else if(LoginType.EMAIL.equals(loginType)) {
            ssoUserOptional = this.ssoUserService.getOneByEmail(arr[1]);
        }
        return ssoUserOptional.map(user -> createSpringSecurityUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException("用户" + username + "不存在或者密码错误"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String username, SsoUser user) {
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("user"));
        if (!user.getActivated() ) {
            throw new UserNotActivatedException("用户" + username + "没有激活");
        }
        return new org.springframework.security.core.userdetails.User(user.getMobile(),
                user.getPwd(),
                grantedAuthorities);
    }
}
