package tech.qdhxy.erp.repository.accounting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.qdhxy.erp.domain.accounting.AccountSubject;

public interface AccountSubjectRepository extends BaseMapper<AccountSubject> {
    @Select("select max(code) maxCode from erp_ac_account_subject where level = 1")
    String selectLevel1MaxCode();

    @Select("select max(code) maxCode from erp_ac_account_subject where pid=#{pid} and level = #{level}")
    String selectMaxCodeByPidAndLevel(@Param("pid") Long pid, @Param("level") int level);
}
