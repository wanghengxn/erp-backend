package tech.qdhxy.erp.service.sso;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.repository.sso.SsoUserRepository;
import tech.qdhxy.erp.security.SecurityUtils;
import tech.qdhxy.erp.service.sso.dto.SsoUserDTO;
import tech.qdhxy.erp.service.sso.mapper.SsoUserMapper;
import tech.qdhxy.erp.web.rest.sso.vm.UpdateCurrentUserPwdVM;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SsoUserService extends ServiceImpl<SsoUserRepository, SsoUser> {
    private final SsoUserMapper ssoUserMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<SsoUser> getOneByMobile(String mobile) {
        return Optional.ofNullable(this.baseMapper.selectOne(Wrappers
                .<SsoUser>lambdaQuery()
                .eq(SsoUser::getMobile, mobile)));
    }

    public Optional<SsoUser> getOneByEmail(String email) {
        return Optional.ofNullable(this.baseMapper.selectOne(Wrappers
                .<SsoUser>lambdaQuery()
                .eq(SsoUser::getEmail, email)));
    }

    public SsoUserDTO getCurrentLoginUser() {
        return SecurityUtils
                .getCurrentUserLogin()
                .flatMap(this::getOneByMobile)
                .map(ssoUserMapper::toDto)
                .orElse(null);
    }

    public void updatePwd(UpdateCurrentUserPwdVM vm) {
        Optional<SsoUser> currentUserOpt = SecurityUtils
                .getCurrentUserLogin()
                .flatMap(this::getOneByMobile);
        if(currentUserOpt.isPresent()) {
            String pwd = currentUserOpt.get().getPwd();
            if(passwordEncoder.matches(vm.getOldPwd(), pwd)) {
                pwd = passwordEncoder.encode(vm.getConfirmNewPwd());
                this.baseMapper.update(null, Wrappers.<SsoUser>lambdaUpdate()
                        .set(SsoUser::getPwd, pwd)
                        .eq(SsoUser::getId, currentUserOpt.get().getId()));
            } else {
                throw new BadRequestException("原密码不正确");
            }
        }
    }
}
