package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;
import tech.qdhxy.erp.repository.accounting.UserAccountSetRepository;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;
import tech.qdhxy.erp.service.accounting.mapper.UserAccountSetMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAccountSetService extends ServiceImpl<UserAccountSetRepository, UserAccountSet> {
    private final UserAccountSetMapper userAccountSetMapper;

    public String getSelectedAccountSetCodeByUserCode(String userCode) {
        return Optional.ofNullable(this.baseMapper.selectOne(Wrappers.<UserAccountSet>lambdaQuery()
                        .eq(UserAccountSet::getUserCode, userCode)
                        .eq(UserAccountSet::getSelected, true)))
                .map(UserAccountSet::getAccountSetCode)
                .orElse("");
    }

    public List<UserAccountSetDTO> getUserAccountSetByUserCode(String userCode) {
        return this.baseMapper.getCurrentUserAccountSet(userCode);
    }

    public long getUserSelectedCount(String userCode) {
        return this.baseMapper.selectCount(Wrappers.<UserAccountSet>lambdaQuery()
                .eq(UserAccountSet::getUserCode, userCode)
                .eq(UserAccountSet::getSelected, true));
    }

    @Transactional
    public UserAccountSetDTO saveRecord(UserAccountSetDTO userAccountSetDTO) {
        long count = getUserSelectedCount(userAccountSetDTO.getUserCode());
        if(count > 0) {
            userAccountSetDTO.setSelected(false);
        } else {
            userAccountSetDTO.setSelected(true);
        }
        if(Objects.nonNull(userAccountSetDTO.getId())) {

        } else {
            UserAccountSet userAccountSet = userAccountSetMapper.toEntity(userAccountSetDTO);
            this.baseMapper.insert(userAccountSet);
        }
        return userAccountSetDTO;
    }
}
