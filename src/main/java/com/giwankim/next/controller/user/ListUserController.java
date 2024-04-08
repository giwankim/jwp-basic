package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.Controller;
import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements Controller {
  private final UserDao userDao;

  public ListUserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public View handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(request.getSession())) {
      return new JspView("redirect:/user/loginForm");
    }
    request.setAttribute("users", userDao.findAll());
    return JspView.from("/user/list.jsp");
  }
}
