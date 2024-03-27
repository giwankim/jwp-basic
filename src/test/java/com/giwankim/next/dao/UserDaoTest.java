package com.giwankim.next.dao;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

  private UserDao userDao;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    populator.addScript(new ClassPathResource("data.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());

    userDao = new UserDao();
  }

  @Test
  void shouldInsert() throws SQLException {
    User user = new User(
      "userId",
      "password",
      "name",
      "test@example.com");

    userDao.insert(user);

    assertThat(userDao.findByUserId("userId"))
      .isPresent()
      .contains(user);
  }

  @Test
  void shouldReturnEmptyWhenUserIdDoesNotExist() throws SQLException {
    assertThat(userDao.findByUserId("does-not-exist")).isEmpty();
  }

  @Test
  void shouldFindAll() throws SQLException {
    List<User> users = userDao.findAll();
    assertThat(users).containsExactly(
      new User("admin", "password", "자바지기", "admin@slipp.net"));
  }
}