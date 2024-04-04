package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowController implements Controller {
  private final QuestionDao questionDao;

  public ShowController(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long questionId = Long.parseLong(request.getParameter("questionId"));
    Question question = questionDao.findById(questionId).orElse(null);
    request.setAttribute("question", question);
    return "/qna/show.jsp";
  }
}
