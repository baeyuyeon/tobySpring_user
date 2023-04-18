package com.springbook.user;

import com.springbook.user.dao.DaoFactory;
import com.springbook.user.dao.UserDao;
import com.springbook.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest2 {
  //  @Autowired
    private UserDao dao;
    User user1;
    User user2;
    User user3;

    @Before
    public void setUp() {
        dao = new UserDao();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3307/testDB?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

                /*"jdbc:mysql://127.0.0.1:3307/testDB?characterEncoding=UTF-8",
                "root", "123456", true);*/

        dao.setDataSource(dataSource);

        user1 = new User("apple", "사과", "123456");
        user2 = new User("banana", "바나나", "123456");
        user3 = new User("lemon", "레몬", "123456");
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);

        assertThat(dao.getCount(), is(2));

        User getUser = dao.get(user1.getId());
        assertThat(getUser.getName(), is(user1.getName()));
        assertThat(getUser.getPassword(), is(user1.getPassword()));

        User getUser2 = dao.get(user2.getId());
        assertThat(getUser2.getName(), is(user2.getName()));
        assertThat(getUser2.getPassword(), is(user2.getPassword()));
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));
        dao.get("unknown_id");
    }
}
