package core.db;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import next.model.User;

public class Database {

  private static final Map<String, User> users = new ConcurrentHashMap<>();

  public static void addUser(User user) {
    users.put(user.getUserId(), user);
  }

  public static Optional<User> findById(String userId) {
    return Optional.ofNullable(users.get(userId));
  }

  public static Collection<User> findAll() {
    return users.values();
  }
}
