package com.springbook.user;

import com.springbook.user.dao.DaoFactory;
import com.springbook.user.dao.UserDao;
import com.springbook.user.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);


        UserDao dao = ac.getBean("userDao", UserDao.class);
        UserDao dao2 = ac.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));


        User user = new User();
        user.setId("9546");
        user.setName("배유연12");
        user.setPassword("1234");
        dao.add(user);
        assertThat(dao.getCount(), is(1));

        User user2 = dao.get(user.getId());
        assertThat(user.getName(), is(user2.getName()));
        assertThat(user.getPassword(), is(user2.getPassword()));
    }
}