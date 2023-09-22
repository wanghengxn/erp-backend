package tech.qdhxy.erp.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.env.Profiles;

import javax.sql.DataSource;
import java.util.concurrent.Executor;

import static tech.qdhxy.erp.config.Constant.SPRING_PROFILE_NO_LIQUIBASE;

@EnableConfigurationProperties(LiquibaseProperties.class)
@Slf4j
@Configuration
public class LiquibaseConfiguration {




    @Bean
    public SpringLiquibase liquibase(@Qualifier("taskExecutor") Executor executor,
                                     DataSource dataSource,
                                     Environment env,
                                     LiquibaseProperties liquibaseProperties) {

        // If you don't want Liquibase to start asynchronously, substitute by this:
        // SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDataSource.getIfAvailable(), liquibaseProperties, dataSource.getIfUnique(), dataSourceProperties);
        SpringLiquibase liquibase = new AsyncSpringLiquibase(executor, env);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        if (env.acceptsProfiles(Profiles.of(SPRING_PROFILE_NO_LIQUIBASE))) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }
}
