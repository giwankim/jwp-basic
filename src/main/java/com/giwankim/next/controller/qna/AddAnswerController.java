package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.Controller;
import com.giwankim.core.mvc.JsonView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAnswerController implements Controller {
  public static final Logger logger = LoggerFactory.getLogger(AddAnswerController.class);

  private final AnswerDao answerDao;

  public AddAnswerController(AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Override
  public View handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Answer answer = new Answer(
      request.getParameter("writer"),
      request.getParameter("contents"),
      Long.parseLong(request.getParameter("questionId")));
    logger.debug("answer : {}", answer);

    Answer savedAnswer = answerDao.insert(answer);
    request.setAttribute("answer", savedAnswer);
    return new JsonView();
  }
}
