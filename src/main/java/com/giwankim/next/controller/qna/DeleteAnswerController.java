package com.giwankim.next.controller.qna;

import com.giwankim.core.jdbc.DataAccessException;
import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAnswerController extends AbstractController {
  private final AnswerDao answerDao;

  public DeleteAnswerController(AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long answerId = Long.parseLong(request.getParameter("answerId"));
    Result result;
    try {
      answerDao.delete(answerId);
      result = Result.ok();
    } catch (DataAccessException dae) {
      result = Result.fail(dae.getMessage());
    }
    return jsonView()
      .addObject("result", result);
  }
}
