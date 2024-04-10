package com.giwankim.next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.giwankim.next.controller.Result;
import com.giwankim.next.dao.AnswerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAnswerControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  @Mock
  AnswerDao answerDao;

  PrintWriter out;

  DeleteAnswerController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    out = mock(PrintWriter.class);
    sut = new DeleteAnswerController(answerDao);
  }

  @Test
  @DisplayName("응답을 DB에서 지운다.")
  void shouldDeleteAnswer() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");
    when(response.getWriter()).thenReturn(out);

    sut.execute(request, response);

    verify(answerDao).delete(1L);
  }

  @Test
  @DisplayName("JSON 형태로 응답한다.")
  void shouldReturnJsonResponse() throws ServletException, IOException {
    when(request.getParameter("answerId")).thenReturn("1");
    when(response.getWriter()).thenReturn(out);

    sut.execute(request, response);

    verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
  }

  @Test
  @DisplayName("응답 바디에 JSON 결과를 반환한다")
  void shouldWriteResultToResponseBody() throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    when(request.getParameter("answerId")).thenReturn("1");
    when(response.getWriter()).thenReturn(out);

    sut.execute(request, response);

    verify(out).print(mapper.writeValueAsString(Result.ok()));
  }
}
