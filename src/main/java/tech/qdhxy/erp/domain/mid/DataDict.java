package tech.qdhxy.erp.domain.mid;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.domain.BaseEntity;

@Getter
@Setter
@TableName("erp_mid_data_dict")
public class DataDict extends BaseEntity {
    private String groupKey;
    private String itemKey;
    private String itemValue;
    private Boolean status;
}
