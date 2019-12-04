package com.example.gohome.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SessionFactoryConfiguration {
    @Value("${mybatis_config_file}")
    private String mybatisConfigPath;
    //mybatis mapper文件所在路径
    @Value("${mapper_path}")
    private String mapperPath;
    //实体类所在的文件
    @Value("${entity_package}")
    private String entityPackage;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean creatSqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigPath));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_URL_PREFIX;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packSearchPath+mapperPath));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return sqlSessionFactoryBean;
    }
}
