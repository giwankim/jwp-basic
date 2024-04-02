package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;


public class LoginController implements Controller {
  private final UserDao userDao;

  public LoginController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    Optional<User> optionalUser = userDao.findByUserId(userId);
    if (optionalUser.isEmpty()) {
      request.setAttribute("loginFailed", true);
      return "/user/login.jsp";
    }
    User user = optionalUser.get();
    if (!user.comparePasswords(password)) {
      request.setAttribute("loginFailed", true);
      return "/user/login.jsp";
    }
    HttpSession session = request.getSession();
    session.setAttribute(SESSION_USER_KEY, user);
    return "redirect:/";
  }
}
