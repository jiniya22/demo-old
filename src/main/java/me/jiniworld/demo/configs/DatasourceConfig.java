package me.jiniworld.demo.configs;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "me.jiniworld.demo.repositories", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@MapperScan(basePackages = {"me.jiniworld.demo.mapper"}, sqlSessionFactoryRef="sqlSessionFactory")
public class DatasourceConfig {
	
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@Primary
    public DataSource dataSource() {
		return DataSourceBuilder.create().build();
    }
	
	@Primary
	@Bean(name = "jpaProperties")
	@ConfigurationProperties(prefix = "spring.jpa")
	public JpaProperties jpaProperties() {
        return new JpaProperties();
    }
	
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, 
			@Qualifier("dataSource") DataSource primaryDataSource,
			@Qualifier("jpaProperties") JpaProperties jpaProperties) {
		return builder
				.dataSource(primaryDataSource)
				.properties(jpaProperties.getProperties())
				.packages("me.jiniworld.demo.models.entities")
				.persistenceUnit("default")
				.build();
	}
	
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
		transactionManager.setNestedTransactionAllowed(true);
		return transactionManager;
	}
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("me.jiniworld.demo.models.entities");
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*-mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
}
