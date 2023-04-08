package com.springbook.user;

import com.springbook.user.dao.*;
import com.springbook.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /*UserDao dao = new OriginDaoFactory().userDao();
        UserDao dao2 = new OriginDaoFactory().userDao();
        System.out.println(dao);
        System.out.println(dao2);*/
        //ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext ac = new GenericXmlApplicationContext("applicationContext.xml");


        UserDao dao = ac.getBean("userDao", UserDao.class);
        UserDao dao2 = ac.getBean("userDao", UserDao.class);
        System.out.println("dao = " + dao);
        System.out.println("dao2 = " + dao2);
        User user = new User();
        user.setId("4444");
        user.setName("배유연12");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println("user2 Name= " + user2.getName());
        System.out.println("user2 Password= " + user2.getPassword());

        System.out.println(user.getId() + " 조회 성공");
    }
}
