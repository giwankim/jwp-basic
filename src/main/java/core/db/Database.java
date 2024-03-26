package core.db;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import next.model.User;

public class Database {
  private Database() {
  }

  private static final Map<String, User> users = new ConcurrentHashMap<>();

  public static void addUser(User user) {
    users.put(user.getUserId(), user);
  }

  public static Optional<User> findById(String userId) {
    if (userId == null) {
      // ConcurrentHashMap은 null키를 허용하지 않는다.
      throw new IllegalArgumentException("UserId가 null입니다.");
    }
    return Optional.ofNullable(users.get(userId));
  }

  public static Collection<User> findAll() {
    return users.values();
  }

  public static void deleteAll() {
    users.clear();
  }
}
