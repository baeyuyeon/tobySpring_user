package com.springbook.user;

import com.springbook.user.dao.ConnectionMaker;
import com.springbook.user.dao.DConnectionMaker;
import com.springbook.user.dao.DaoFactory;
import com.springbook.user.dao.UserDao;
import com.springbook.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //ConnectionMaker connectionMaker = new DConnectionMaker();
        //UserDao dao = new UserDao(connectionMaker);
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao = ac.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("dbdus9812");
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