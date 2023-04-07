package com.springbook.user.dao;

public class OriginDaoFactory {
    public UserDao userDao(){
        ConnectionMaker connectionMaker = new DConnectionMaker();
        //UserDao userDao = new UserDao(connectionMaker);
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker);
        return userDao;
    }
}
