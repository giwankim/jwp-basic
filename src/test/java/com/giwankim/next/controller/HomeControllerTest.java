package com.giwankim.next.controller;

import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.giwankim.Fixtures.aQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HomeControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HomeController sut;

  QuestionDao questionDao;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    questionDao = mock(QuestionDao.class);
    sut = new HomeController(questionDao);
  }

  @Test
  void shouldReturnView() throws ServletException, IOException {
    String view = sut.execute(request, response);
    assertThat(view).isEqualTo("home.jsp");
  }

  @Test
  void shouldAttachUsers() throws ServletException, IOException {
    List<Question> questions = List.of(
      aQuestion().questionId(1L).build(),
      aQuestion().questionId(2L).build());
    when(questionDao.findAll())
      .thenReturn(questions);

    sut.execute(request, response);

    verify(request).setAttribute("questions", questions);
  }
}
