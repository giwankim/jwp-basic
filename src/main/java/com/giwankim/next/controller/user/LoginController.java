package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;

public class LoginController extends AbstractController {
  private final UserDao userDao;

  public LoginController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    Optional<User> optionalUser = userDao.findByUserId(userId);
    if (optionalUser.isEmpty()) {
      return jspView("/user/login.jsp")
        .addObject("loginFailed", true);
    }
    User user = optionalUser.get();
    if (!user.comparePasswords(password)) {
      return jspView("/user/login.jsp")
        .addObject("loginFailed", true);
    }
    HttpSession session = request.getSession();
    session.setAttribute(SESSION_USER_KEY, user);
    return jspView("redirect:/");
  }
}
