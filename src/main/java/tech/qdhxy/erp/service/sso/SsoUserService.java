package tech.qdhxy.erp.service.sso;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.repository.sso.SsoUserRepository;
import tech.qdhxy.erp.security.SecurityUtils;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SsoUserService extends ServiceImpl<SsoUserRepository, SsoUser> {
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

    public SsoUser getCurrentLoginUser() {
        return SecurityUtils.getCurrentUserLogin().flatMap(this::getOneByMobile).orElse(null);
    }
}
