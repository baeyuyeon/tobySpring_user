package com.springbook.user.service;


import com.springbook.user.dao.UserDao;
import com.springbook.user.dao.UserDaoJdbc;
import com.springbook.user.domain.Level;
import com.springbook.user.domain.User;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.springbook.user.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;


    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("yuyeon1", "유연1", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("yuyeon2", "유연2", "p2", Level.BASIC,  MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("yuyeon3", "유연3", "p3", Level.SILVER, MIN_RECCOMEND_FOR_GOLD-1, 29),
                new User("yuyeon4", "유연4", "p4", Level.SILVER, MIN_RECCOMEND_FOR_GOLD, 30),
                new User("yuyeon5", "유연5", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void upgradeLevels() throws Exception{
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }
        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

    }

    @Test
    public void upgradeAllOrNothing() throws Exception{
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao((UserDaoJdbc) this.userDao);
        testUserService.setDataSource(this.dataSource);
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }
        try{
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        }catch (TestUserServiceException e){

        }
        checkLevel(users.get(1), false);
    }
    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    static class TestUserService extends UserService{
        private String id;

        private TestUserService(String id) {
            this.id = id;
        }
        protected void upgradeLevel(User user){
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }
    static class TestUserServiceException extends RuntimeException{

    }
}