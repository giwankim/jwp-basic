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
import java.util.Optional;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

  private UserDao sut;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());
    sut = new UserDao();
  }

  @Test
  void shouldInsert() throws SQLException {
    User user = aUser().build();

    User actual = sut.insert(user);

    assertThat(actual).isEqualTo(user);
  }

  @Test
  void shouldFindUserById() throws SQLException {
    User user = aUser().build();
    sut.insert(user);

    Optional<User> actual = sut.findByUserId(user.getUserId());

    assertThat(actual)
      .isPresent()
      .contains(user);
  }

  @Test
  void shouldReturnEmptyWhenUserIdDoesNotExist() throws SQLException {
    assertThat(sut.findByUserId("does-not-exist"))
      .isEmpty();
  }

  @Test
  void shouldFindAll() throws SQLException {
    User user1 = aUser()
      .userId("user1")
      .build();
    User user2 = aUser()
      .userId("user2")
      .build();
    sut.insert(user1);
    sut.insert(user2);

    List<User> users = sut.findAll();
    assertThat(users).containsExactly(user1, user2);
  }

  @Test
  void shouldUpdate() throws SQLException {
    User user = aUser().build();
    sut.insert(user);
    User expected = aUser()
      .name("updated")
      .email("updated@example.com")
      .build();

    User actual = sut.update(expected);

    assertThat(actual).isEqualTo(expected);
  }
}
