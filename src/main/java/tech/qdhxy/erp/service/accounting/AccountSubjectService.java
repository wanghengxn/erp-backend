package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.common.utils.PinyinUtil;
import tech.qdhxy.erp.domain.accounting.AccountSubject;
import tech.qdhxy.erp.domain.accounting.AccountSubjectTpl;
import tech.qdhxy.erp.repository.accounting.AccountSubjectRepository;
import tech.qdhxy.erp.repository.accounting.AccountSubjectTplRepository;
import tech.qdhxy.erp.service.accounting.dto.AccountSetDTO;
import tech.qdhxy.erp.service.accounting.dto.AccountSubjectDTO;
import tech.qdhxy.erp.service.accounting.mapper.AccountSubjectMapper;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSubjectQuery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
                            subject.setDef(true);
                            return subject;
                        })
                        .collect(Collectors.toList());
        this.saveBatch(accountSubjects);
    }

    public IPage<AccountSubjectDTO> getPage(AccountSubjectQuery query) {
        return this.baseMapper.selectPage(query.getPage(), query.getWrapper())
                .convert(accountSubjectMapper::toDto);
    }

    @Transactional
    public void updateStateByIds(List<Long> ids, boolean nowStatus) {
        LambdaUpdateWrapper<AccountSubject> wrapper = Wrappers.<AccountSubject>lambdaUpdate()
                .set(AccountSubject::getStatus, nowStatus)
                .in(AccountSubject::getId, ids);
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        this.executeBatch(ids, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> {
            MapperMethod.ParamMap param = new MapperMethod.ParamMap();
            param.put(Constants.ENTITY, null);
            param.put(Constants.WRAPPER, wrapper);
            sqlSession.update(sqlStatement, param);
        });
    }

    public AccountSubjectDTO saveRecord(AccountSubjectDTO accountSubjectDTO) {
        if(Objects.nonNull(accountSubjectDTO.getId())) {
            this.baseMapper.update(null, Wrappers.<AccountSubject>lambdaUpdate()
                    .set(AccountSubject::getStatus, accountSubjectDTO.getStatus())
                    .set(AccountSubject::getCode, accountSubjectDTO.getCode())
                    .set(AccountSubject::getName, accountSubjectDTO.getName())
                    .set(AccountSubject::getDirection, accountSubjectDTO.getDirection())
                    .eq(AccountSubject::getId, accountSubjectDTO.getId()));
            return accountSubjectDTO;
        } else {
            String fastCode = PinyinUtil.getCaptions(accountSubjectDTO.getName());
            accountSubjectDTO.setFastCode(fastCode);
            // code
            int level = accountSubjectDTO.getLevel();
            String nextCode;
            if (level == 1) {
                String maxCode = this.baseMapper.selectLevel1MaxCode();
                int next = Integer.parseInt(maxCode) + 1000;
                nextCode = next + "";
                if (nextCode.length() >= 5) {
                    throw new BadRequestException("一级科目编码已超过最大限度");
                }
            } else {
                String maxCode = this.baseMapper.selectMaxCodeByPidAndLevel(accountSubjectDTO.getPid(), level);
                int next = Integer.parseInt(maxCode) + 1;
                nextCode = next + "";
                if (nextCode.length() > 3) {
                    throw new BadRequestException("非一级科目编码已超过最大限度");
                }
                if (nextCode.length() == 1) {
                    nextCode = "0" + nextCode;
                }
            }
            accountSubjectDTO.setCode(nextCode);
            AccountSubject accountSubject = accountSubjectMapper.toEntity(accountSubjectDTO);
            this.baseMapper.insert(accountSubject);
            return accountSubjectMapper.toDto(accountSubject);
        }
    }

    public AccountSubjectDTO getOneById(Long id) {
        return Optional.ofNullable(this.baseMapper.selectById(id)).map(accountSubjectMapper::toDto).orElse(null);
    }

    public void deleteById(Long id) {
        AccountSubjectDTO dto = this.getOneById(id);
        if(Objects.nonNull(dto)) {
            if(dto.getDef()) {
                throw new BadRequestException("系统预设科目不允许删除");
            } else {
                this.baseMapper.deleteById(id);
            }
        }
    }
}
