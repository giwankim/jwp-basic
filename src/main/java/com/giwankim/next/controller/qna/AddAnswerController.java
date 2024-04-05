package com.giwankim.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddAnswerController implements Controller {
  public static final Logger logger = LoggerFactory.getLogger(AddAnswerController.class);

  private final AnswerDao answerDao;

  public AddAnswerController(AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Answer answer = new Answer(
      request.getParameter("writer"),
      request.getParameter("contents"),
      Long.parseLong(request.getParameter("questionId")));
    logger.debug("answer : {}", answer);

    Answer savedAnswer = answerDao.insert(answer);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    PrintWriter out = response.getWriter();
    out.print(objectMapper.writeValueAsString(savedAnswer));
    return null;
  }
}
