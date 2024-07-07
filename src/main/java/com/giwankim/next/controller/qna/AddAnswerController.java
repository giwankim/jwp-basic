package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAnswerController extends AbstractController {
  public static final Logger logger = LoggerFactory.getLogger(AddAnswerController.class);

  private final QuestionDao questionDao;

  private final AnswerDao answerDao;

  public AddAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
    this.questionDao = questionDao;
    this.answerDao = answerDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long questionId = Long.parseLong(request.getParameter("questionId"));
    Answer answer = new Answer(
      request.getParameter("writer"),
      request.getParameter("contents"),
      questionId);
    logger.debug("answer : {}", answer);

    Answer savedAnswer = answerDao.insert(answer);
    questionDao.incrementAnswerCount(questionId);

    return jsonView()
      .addObject("answer", savedAnswer);
  }
}
