package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.core.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ListUserController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(request.getSession())) {
      return "redirect:/user/loginForm";
    }
    request.setAttribute("users", Database.findAll());
    return "/user/list.jsp";
  }
}