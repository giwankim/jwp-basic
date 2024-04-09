package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;
import com.giwankim.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateQuestionController extends AbstractController {
  public static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);

  private final QuestionDao questionDao;

  public CreateQuestionController(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(request.getSession())) {
      return jspView("redirect:/user/loginForm");
    }
    User user = UserSessionUtils.getUserFromSession(request.getSession());
    Question question = new Question(
      user.getUserId(),
      request.getParameter("title"),
      request.getParameter("contents"));
    logger.debug("question : {}", question);

    questionDao.insert(question);

    return jspView("redirect:/");
  }
}
