package com.giwankim.next.controller.qna;

import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Answer;
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
import java.util.Optional;

import static com.giwankim.Fixtures.aQuestion;
import static com.giwankim.Fixtures.anAnswer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowControllerTest {
  @Mock
  QuestionDao questionDao;

  @Mock
  AnswerDao answerDao;

  HttpServletRequest request;

  HttpServletResponse response;

  ShowController sut;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    sut = new ShowController(questionDao, answerDao);
  }

  @Test
  @DisplayName("답변 페이지를 리턴한다.")
  void shouldReturnQnAShowPage() throws ServletException, IOException {
    when(request.getParameter("questionId")).thenReturn("1");

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("/qna/show.jsp");
  }

  @Test
  @DisplayName("Request의 \"question\" 속성을 세팅한다.")
  void shouldSetQuestions() throws ServletException, IOException {
    Question question = aQuestion().questionId(1L).build();
    when(request.getParameter("questionId")).thenReturn("1");
    when(questionDao.findById(1L)).thenReturn(Optional.of(question));

    sut.execute(request, response);

    verify(request).setAttribute("question", question);
  }

  @Test
  @DisplayName("Request 의\"answers\" 속성을 세팅한다.")
  void shouldSetAnswers() throws ServletException, IOException {
    Question question = aQuestion().build();
    List<Answer> answers = List.of(
      anAnswer().answerId(1L).build(),
      anAnswer().answerId(2L).build());
    when(request.getParameter("questionId")).thenReturn("1");
    when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(answers);

    sut.execute(request, response);

    verify(request).setAttribute("answers", answers);
  }
}
