package com.giwankim.next.controller;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.QuestionDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends AbstractController {
  private final QuestionDao questionDao;

  public HomeController(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    return jspView("home.jsp")
      .addObject("questions", questionDao.findAll());
  }
}
