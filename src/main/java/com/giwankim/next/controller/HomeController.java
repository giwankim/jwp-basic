package com.giwankim.next.controller;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController implements Controller {

  private final UserDao userDao;

  public HomeController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("users", userDao.findAll());
    return "home.jsp";
  }
}
