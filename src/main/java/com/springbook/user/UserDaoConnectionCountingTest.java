package com.springbook.user;

import com.springbook.user.dao.CountingConnectionMaker;
import com.springbook.user.dao.CountingDaoFactory;
import com.springbook.user.dao.UserDao;
import com.springbook.user.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("2222");
        user.setName("배유연12");
        user.setPassword("1234");

        userDao.add(user);
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCount());

    }
}
