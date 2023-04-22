package com.springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration //애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    @Bean
    public UserDao userDao(){
        //return new UserDao(connectionMaker());
        UserDaoJdbc userDao = new UserDaoJdbc();
        //userDao.setConnectionMaker(connectionMaker());
        userDao.setDataSource(dataSource());
        return userDao;
    }

    /*@Bean
    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }*/
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3307/userDB?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        return dataSource;
    }
}
