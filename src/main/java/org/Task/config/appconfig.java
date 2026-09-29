package org.Task.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.Task")
public class appconfig {
    @Bean
    public DataSource dataSource() {
        var ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:OnlineShop;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        return ds;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        var sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds);
        sf.setPackagesToScan("org.Task.model");
        Properties p = new Properties();
        p.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        p.put("hibernate.hbm2ddl.auto", "create-drop");
        p.put("hibernate.show_sql", "true");
        p.put("hibernate.format_sql", "true");
        sf.setHibernateProperties(p);
        return sf;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }

    @Bean
    public  PersistenceExceptionTranslationPostProcessor translator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


}
