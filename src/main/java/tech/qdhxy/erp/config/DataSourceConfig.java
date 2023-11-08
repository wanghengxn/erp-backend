package tech.qdhxy.erp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@EnableTransactionManagement
@Configuration
@MapperScan("tech.qdhxy.erp.repository")
public class DataSourceConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Component
    static class AuditMetaObjectHandler implements MetaObjectHandler {
        /**
         * 插入时的填充策略
         *
         * @param metaObject
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            Date now = new Date();
            String currentAuditor = this.getCurrentAuditor();
            // 创建人
            setFieldValByName("createdBy", currentAuditor, metaObject);
            // 创建时间
            setFieldValByName("createdDate", now, metaObject);
            // 最后修改人
            setFieldValByName("lastModifiedBy", currentAuditor, metaObject);
            // 创建人
            setFieldValByName("lastModifiedDate", now, metaObject);
        }

        /**
         * 更新时的填充策略
         *
         * @param metaObject
         */
        @Override
        public void updateFill(MetaObject metaObject) {
            Date now = new Date();
            String currentAuditor = getCurrentAuditor();
            // 最后修改人
            setFieldValByName("lastModifiedBy", currentAuditor, metaObject);
            // 创建人
            setFieldValByName("lastModifiedDate", now, metaObject);
        }

        private String getCurrentAuditor() {
            return "111";
        }

    }
}
