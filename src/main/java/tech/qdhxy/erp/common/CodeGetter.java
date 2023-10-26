package tech.qdhxy.erp.common;

import org.springframework.stereotype.Component;
import tech.qdhxy.erp.common.utils.SequenceGenerator;

import javax.annotation.PostConstruct;

@Component
public class CodeGetter {
    @PostConstruct
    private void init() {
        SequenceGenerator.getInstance().nextId();
    }

    public String fetch() {
        return String.valueOf(SequenceGenerator.getInstance().nextId());
    }
}
