package next.controller;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
  public static final String SESSION_USER_KEY = "user";

  private UserSessionUtils() {
  }

  public static boolean isLoggedIn(HttpSession session) {
    return session.getAttribute(SESSION_USER_KEY) != null;
  }
}
