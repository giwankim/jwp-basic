package com.giwankim.next.controller;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.QuestionDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController implements Controller {
  private final QuestionDao questionDao;

  public HomeController(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("questions", questionDao.findAll());
    return "home.jsp";
  }
}
