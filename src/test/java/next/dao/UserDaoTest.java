package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;
import java.util.Optional;

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
  void shouldInsertUser() throws SQLException {
    User user = new User(
      "userId",
      "password",
      "name",
      "expected@test.com");
    Optional<User> expected = Optional.of(user);

    userDao.insert(user);

    assertThat(userDao.findById("userId")).isEqualTo(expected);
  }

  @Test
  void shouldReturnEmptyWhenUserIdDoesNotExist() throws SQLException {
    assertThat(userDao.findById("doesNotExist")).isEmpty();
  }
}