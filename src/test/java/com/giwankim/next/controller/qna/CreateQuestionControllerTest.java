package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.JspView;
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

import static com.giwankim.Fixtures.aQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateQuestionControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  QuestionDao questionDao;

  CreateQuestionController sut;

  @BeforeEach
  void setUp() {
    sut = new CreateQuestionController(questionDao);
  }

  @Test
  @DisplayName("질문을 저장한다.")
  void shouldSaveQuestion() throws ServletException, IOException {
    Question question = aQuestion().build();
    when(request.getParameter("writer")).thenReturn(question.getWriter());
    when(request.getParameter("title")).thenReturn(question.getTitle());
    when(request.getParameter("contents")).thenReturn(question.getContents());

    sut.handleRequest(request, response);

    verify(questionDao).insert(argThat(q ->
      question.getWriter().equals(q.getWriter()) &&
        question.getTitle().equals(q.getTitle()) &&
        question.getContents().equals(q.getContents())));
  }

  @Test
  @DisplayName("질문하기 생성에 성공한 후 질문 목록 페이지로 이동한다.")
  void shouldRedirect() throws ServletException, IOException {
    ModelAndView mv = sut.handleRequest(request, response);
    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/"));
  }
}
