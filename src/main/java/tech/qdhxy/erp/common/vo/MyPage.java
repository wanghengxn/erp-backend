package tech.qdhxy.erp.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPage<T> {
    private List<T> data;
    private PageVO pageVO;

    public MyPage() {

    }

    public static <T> MyPage<T> of(IPage<T> page){
        MyPage<T> myPage = new MyPage<>();
        PageVO pageVO = new PageVO();
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());

        myPage.setData(page.getRecords());
        myPage.setPageVO(pageVO);
        return myPage;
    }

    @Getter
    @Setter
    public static final class PageVO {
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
    }
}
