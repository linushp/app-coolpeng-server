package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ResourceBundle;

public class ServiceUtils
        implements BeanFactoryAware {
    private static Logger logger = LoggerFactory.getLogger(ServiceUtils.class);

    private static BeanFactory beanFactory = null;
    private static JdbcTemplate jdbcTemplateService;
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplateService;
    private static ResourceBundle RB = ResourceBundle.getBundle("db-connection");

    public static ResourceBundle getResourceBundle() {
        return RB;
    }

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplateService == null) {
            Object jdbcTemplateService = getBean("jdbcTemplate");
            if (jdbcTemplateService == null) {
                logger.error("jdbcTemplateService is null");

                DriverManagerDataSource ds = new DriverManagerDataSource();
                ds.setDriverClassName("com.mysql.jdbc.Driver");
                ds.setUrl(RB.getString("connection.url"));
                ds.setUsername(RB.getString("connection.username"));
                ds.setPassword(RB.getString("connection.password"));
                JdbcTemplate t = new JdbcTemplate();
                t.setDataSource(ds);
                jdbcTemplateService = t;
            }
            ServiceUtils.jdbcTemplateService = (JdbcTemplate) jdbcTemplateService;
        }
        return jdbcTemplateService;
    }

    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        if (namedParameterJdbcTemplateService == null) {
            Object namedParameterJdbcTemplateService = getBean("namedParameterJdbcTemplate");
            if (namedParameterJdbcTemplateService == null) {
                JdbcTemplate jdbcTemplate = getJdbcTemplate();
                if (jdbcTemplate == null) {
                    logger.error("jdbcTemplate is null");
                    return null;
                }
                namedParameterJdbcTemplateService = new NamedParameterJdbcTemplate(jdbcTemplate);
            }
            ServiceUtils.namedParameterJdbcTemplateService = (NamedParameterJdbcTemplate) namedParameterJdbcTemplateService;
        }
        return namedParameterJdbcTemplateService;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public static Object getBean(String name) {
        if (beanFactory == null) {
            logger.error("beanFactory is null");
            return null;
        }
        return beanFactory.getBean(name);
    }
}