package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qdhxy.erp.domain.accounting.AccountSubject;
import tech.qdhxy.erp.domain.accounting.AccountSubjectTpl;
import tech.qdhxy.erp.repository.accounting.AccountSubjectRepository;
import tech.qdhxy.erp.repository.accounting.AccountSubjectTplRepository;
import tech.qdhxy.erp.service.accounting.dto.AccountSubjectDTO;
import tech.qdhxy.erp.service.accounting.mapper.AccountSubjectMapper;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSubjectQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountSubjectService extends ServiceImpl<AccountSubjectRepository, AccountSubject> {
    private final AccountSubjectTplRepository accountSubjectTplRepository;
    private final AccountSubjectMapper accountSubjectMapper;

    @Transactional
    public void copyTpl(String accountSetCode) {
        List<AccountSubject> accountSubjects =
                this.accountSubjectTplRepository.selectList(Wrappers.emptyWrapper())
                        .stream()
                        .map(tpl -> {
                            AccountSubject subject = accountSubjectMapper.fromTpl(tpl);
                            subject.setAccountSetCode(accountSetCode);
                            return subject;
                        })
                        .collect(Collectors.toList());
        this.saveBatch(accountSubjects);
    }

    public IPage<AccountSubjectDTO> getPage(AccountSubjectQuery query) {
        return this.baseMapper.selectPage(query.getPage(), query.getWrapper())
                .convert(accountSubjectMapper::toDto);
    }
}
