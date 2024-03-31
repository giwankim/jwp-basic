package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.core.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("users", Database.findAll());
    return "home.jsp";
  }
}
