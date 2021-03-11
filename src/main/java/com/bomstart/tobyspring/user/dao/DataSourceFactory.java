package com.bomstart.tobyspring.user.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceFactory {
    /**
     * DB에 상관없이 DataSource를 만드는데 필요한 정보를 application.properties 에서 가져와서 사용
     * 이렇게 하면 DB가 변경되어도 Java 코드를 새로 컴파일하지 않아도 되는 이점이 있다.
     */
    @Value("${driver}")
    private String driver;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    /**
     * DataSource Bean
     *
     * DB Connection 관심사를 UserDao에서 별도 Bean으로 분리
     *
     * @return
     * @throws ClassNotFoundException
     */
    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        Class driverClass = Class.forName(this.driver);
        dataSource.setDriverClass(driverClass);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }

}
