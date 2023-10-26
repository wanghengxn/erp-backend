package tech.qdhxy.erp.repository.accounting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;

import java.util.List;

public interface UserAccountSetRepository extends BaseMapper<UserAccountSet> {
    String getCurrentUserAccountSet = "SELECT a.*,\n" +
            "       b.NAME        AS accountSetName,\n" +
            "       b.start_year  AS accountSetStartYear,\n" +
            "       b.start_month AS accountSetStartMonth\n" +
            "FROM erp_ac_user_account_set a\n" +
            "         JOIN erp_ac_account_set b ON a.account_set_code = b.`code`\n" +
            "WHERE b.`status` = 1\n" +
            "  AND a.user_code = #{userCode};\n";
    @Select(getCurrentUserAccountSet)
    List<UserAccountSetDTO> getCurrentUserAccountSet(@Param("userCode") String userCode);
}
