package tech.qdhxy.erp.common.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPage<T> {
    private List<T> records;
    private long total;
    private long size;
    private long current;
    private long totalPage;

    public long getTotalPage() {
        if (total <= 0L || size <= 0L) {
            return 0;
        }
        return (total / size) + (total % size == 0 ? 0 : 1);
    }

    public MyPage() {

    }

    public static <T> MyPage<T> of(Page<T> page){
        MyPage<T> myPage = new MyPage<T>();
        myPage.setSize(page.getSize());
        myPage.setRecords(page.getRecords());
        myPage.setTotal(page.getTotal());
        myPage.setCurrent(page.getCurrent());
        return myPage;
    }
}
