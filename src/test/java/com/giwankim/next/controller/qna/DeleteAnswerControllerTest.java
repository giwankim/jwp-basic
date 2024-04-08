package com.giwankim.next.controller.qna;

import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAnswerControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AnswerDao answerDao;

  DeleteAnswerController sut;

  @BeforeEach
  void setUp() {
    sut = new DeleteAnswerController(answerDao);
  }

  @Test
  @DisplayName("응답을 지운다.")
  void shouldDeleteAnswer() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");

    sut.handleRequest(request, response);

    verify(answerDao).delete(1L);
  }

  @Test
  @DisplayName("응답 바디에 결과를 반환한다")
  void shouldWriteResultToResponseBody() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");

    sut.handleRequest(request, response);

    verify(request).setAttribute("result", Result.ok());
  }
}
