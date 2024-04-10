package com.giwankim.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.giwankim.core.mvc.Controller;
import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {
  private final AnswerDao answerDao;

  public DeleteAnswerController(AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    long answerId = Long.parseLong(request.getParameter("answerId"));
    answerDao.delete(answerId);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter out = response.getWriter();
    out.print(mapper.writeValueAsString(Result.ok()));
    return null;
  }
}
