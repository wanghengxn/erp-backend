package tech.qdhxy.erp.web.rest.mid.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.common.utils.Constants;
import tech.qdhxy.erp.domain.mid.DataDict;

@Getter
@Setter
public class DataDictQuery extends PageQuery<DataDict> {
    private String groupKey;
    private String itemValue;
    @Override
    public Wrapper<DataDict> getWrapper() {
        LambdaQueryWrapper<DataDict> wrapper = Wrappers.<DataDict>lambdaQuery().ne(DataDict::getGroupKey, Constants.DATA_DICT_META_GROUP);
        if(StringUtils.isNotBlank(groupKey)) {
            wrapper.eq(DataDict::getGroupKey, groupKey);
        }
        if(StringUtils.isNotBlank(itemValue)) {
            wrapper.like(DataDict::getItemValue, "%" + itemValue + "%");
        }

        return wrapper;
    }
}
