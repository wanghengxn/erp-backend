package tech.qdhxy.erp.common.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class PageQuery<T> {
    @NotNull
    @Min(value = 1, message = "最小值为1")
    private long current;
    @NotNull
    @Min(value = 1,message = "最小值为1")
    private long size;

    public IPage<T> getPage() {
        return new Page<>(this.current, this.size);
    }

    public abstract Wrapper<T> getWrapper();

}
