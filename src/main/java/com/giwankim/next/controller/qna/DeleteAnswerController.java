package com.giwankim.next.controller.qna;

import com.giwankim.core.jdbc.DataAccessException;
import com.giwankim.core.mvc.Controller;
import com.giwankim.core.mvc.JsonView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAnswerController implements Controller {
  private final AnswerDao answerDao;

  public DeleteAnswerController(AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Override
  public View handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long answerId = Long.parseLong(request.getParameter("answerId"));
    try {
      answerDao.delete(answerId);
      request.setAttribute("result", Result.ok());
    } catch (DataAccessException dae) {
      request.setAttribute("result", Result.fail(dae.getMessage()));
    }
    return new JsonView();
  }
}
