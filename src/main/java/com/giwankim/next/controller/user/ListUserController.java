package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController extends AbstractController {
  private final UserDao userDao;

  public ListUserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(request.getSession())) {
      return jspView("redirect:/user/loginForm");
    }
    return jspView("/user/list.jsp")
      .addObject("users", userDao.findAll());
  }
}
