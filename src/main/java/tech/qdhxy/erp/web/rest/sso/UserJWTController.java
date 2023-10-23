package tech.qdhxy.erp.web.rest.sso;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.security.jwt.JWTFilter;
import tech.qdhxy.erp.security.jwt.TokenProvider;
import tech.qdhxy.erp.web.rest.sso.vm.LoginVM;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/sso")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    //private final SsoUserService ssoUserService;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getLoginType() + ":" + loginVM.getUsername(), loginVM.getPassword());

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = loginVM.getRememberMe() != null && loginVM.getRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException("用户名或密码错误");
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    public static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
//    @GetMapping("/send-mobile")
//    public void sendMobile(@RequestParam String mobile) {
//        boolean exists = userService.isMobileExists(mobile);
//
//        if (!exists) {
//            throw new BadRequestAlertException("手机号不存在在系统内", "User", "手机号不存在在系统内");
//        }
//
//        int code = (int) (Math.random() * 9000 + 1000);
//        String c = code + "";
//        SendSmsResponse ret = MessageUtil.sendMsg(mobile, c);
//        if (Objects.nonNull(ret) && "OK".equals(ret.getBody().getCode())) {
//            redisTemplate.opsForValue().set(mobile, c);
//        }
//    }
//
//    @PostMapping("/authenticate/mobile")
//    public ResponseEntity<JWTToken> authorizeMobile(@Valid @RequestBody MobileLoginVM loginVM) {
//        User user = userService.getByMobile(loginVM.getMobile());
//        if (Objects.isNull(user)) {
//            throw new BadRequestAlertException("手机号或者验证码错误");
//        }
//        LocalDate now = LocalDate.now();
//        if (!user.isActivated()) {
//            throw new UserNotActivatedException("User " + loginVM.getMobile() + " was not activated");
//        } else {
//            if (UserType.WEB.equals(user.getType())) {
//                if (Objects.isNull(user.getExpirationTime()) || now.compareTo(user.getExpirationTime()) >= 0) {
//                    throw new UserNotActivatedException("账号不在有效期内");
//                }
//            }
//        }
//
//        String code = redisTemplate.opsForValue().get(loginVM.getMobile());
//        // TODO 改成真实的短信验证码校验
//        if (Objects.isNull(user) || !loginVM.getCode().equals(code)) {
//            throw new BadRequestAlertException("手机号或者验证码错误", "User", "手机号或者验证码错误");
//        }
//
//        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(user.getMobile(),
//            user.getPwd(),
//            authorities);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        boolean rememberMe = (loginVM.getRememberMe() == null) ? false : loginVM.getRememberMe();
//        String jwt = tokenProvider.createToken(authentication, rememberMe);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
//    }

}
