package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.JsonView;
import com.giwankim.core.mvc.ModelAndView;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiListQuestionsControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  QuestionDao questionDao;

  ApiListQuestionsController sut;

  @BeforeEach
  void setUp() {
    sut = new ApiListQuestionsController(questionDao);
  }

  @Test
  @DisplayName("json 데이터 응답이다.")
  void shouldReturnJson() throws ServletException, IOException {
    ModelAndView mv = sut.handleRequest(request, response);
    assertThat(mv.getView()).isInstanceOf(JsonView.class);
  }

  @Test
  @DisplayName("질문 목록이 응답에 포함된다.")
  void shouldAddQuestionsToResponse() throws ServletException, IOException {
    List<Question> questions = List.of(
      aQuestion().questionId(1L).build(),
      aQuestion().questionId(2L).build());
    when(questionDao.findAll()).thenReturn(questions);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("questions", questions);
  }
}
