package next.controller;

import next.model.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
  public static final String SESSION_USER_KEY = "user";

  private UserSessionUtils() {
  }

  public static User getUserFromSession(HttpSession session) {
    return (User) session.getAttribute(SESSION_USER_KEY);
  }

  public static boolean isLoggedIn(HttpSession session) {
    return getUserFromSession(session) != null;
  }

  public static boolean isSameUser(HttpSession session, User user) {
    if (!isLoggedIn(session) || user == null) {
      return false;
    }
    return user.isSameUser(getUserFromSession(session));
  }
}
