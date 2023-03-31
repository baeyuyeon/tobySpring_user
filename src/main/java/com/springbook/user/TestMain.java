package com.springbook.user;

import com.springbook.user.dao.UserDao;
import com.springbook.user.domain.User;

import java.sql.SQLException;

public class TestMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();
        
        User user = new User();
        user.setId("dbdus98312");
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
