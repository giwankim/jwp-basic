package com.giwankim.next.controller;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;
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
import java.util.List;

import static com.giwankim.Fixtures.aQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  QuestionDao questionDao;

  HomeController sut;

  @BeforeEach
  void setUp() {
    sut = new HomeController(questionDao);
  }

  @Test
  @DisplayName("홈 페이지를 서빙한다.")
  void shouldReturnView() throws ServletException, IOException {
    View view = sut.handleRequest(request, response);

    assertThat(view).isEqualTo(JspView.from("home.jsp"));
  }

  @Test
  @DisplayName("Questions 속성을 초가한다.")
  void shouldAttachQuestions() throws ServletException, IOException {
    List<Question> questions = List.of(
      aQuestion().questionId(1L).build(),
      aQuestion().questionId(2L).build());
    when(questionDao.findAll()).thenReturn(questions);

    sut.handleRequest(request, response);

    verify(request).setAttribute("questions", questions);
  }
}
