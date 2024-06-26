package com.duzce.captcha.config;

import com.duzce.captcha.model.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import static org.hibernate.cfg.C3p0Settings.C3P0_MAX_SIZE;
import static org.hibernate.cfg.C3p0Settings.C3P0_MIN_SIZE;
import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.MappingSettings.DEFAULT_SCHEMA;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;

@PropertySource(value= "classpath:hibernate.properties", encoding = "UTF-8")
@EnableTransactionManagement
@Configuration
@ComponentScan(basePackages = {"com.duzce.captcha"})
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        Properties props = new Properties();
        props.put(DRIVER, env.getProperty("postgres.driver"));
        props.put(URL, env.getProperty("postgres.url"));
        props.put(USER, env.getProperty("postgres.user"));
        props.put(PASS, env.getProperty("postgres.password"));
        props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
        props.put(DIALECT, env.getProperty("hibernate.dialect"));
        props.put(DEFAULT_SCHEMA, env.getProperty("hibernate.default_schema"));

        props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
        props.put(C3P0_CONFIG_PREFIX + ".initialPoolSize", env.getProperty("hibernate.c3p0.initialPoolSize"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(Captcha.class);

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

}
