package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UnauthorizedException;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateQuestionFormController extends AbstractController {
  private final QuestionDao questionDao;

  public UpdateQuestionFormController(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(request.getSession())) {
      return jspView("redirect:/user/loginForm");
    }

    long questionId = Long.parseLong(request.getParameter("questionId"));
    Question question = questionDao.findById(questionId)
      .orElseThrow(() -> new QuestionNotFoundException("질문을 찾을 수 없습니다."));

    User user = UserSessionUtils.getUserFromSession(request.getSession());
    if (!user.isWriter(question)) {
      throw new UnauthorizedException("질문 작성자만이 수정할 수 있습니다.");
    }

    return jspView("/qna/updateForm.jsp")
      .addObject("question", question);
  }
}
