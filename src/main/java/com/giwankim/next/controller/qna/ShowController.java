package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.Controller;
import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Answer;
import com.giwankim.next.model.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowController implements Controller {
  private final QuestionDao questionDao;
  private final AnswerDao answerDao;

  public ShowController(QuestionDao questionDao, AnswerDao answerDao) {
    this.questionDao = questionDao;
    this.answerDao = answerDao;
  }

  @Override
  public View handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long questionId = Long.parseLong(request.getParameter("questionId"));
    Question question = questionDao.findById(questionId).orElse(null);
    List<Answer> answers = answerDao.findAllByQuestionId(questionId);
    request.setAttribute("question", question);
    request.setAttribute("answers", answers);
    return JspView.from("/qna/show.jsp");
  }
}
