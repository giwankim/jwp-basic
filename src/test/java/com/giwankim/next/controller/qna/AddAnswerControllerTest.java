package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.AnswerDao;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Answer;
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

import static com.giwankim.Fixtures.anAnswer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddAnswerControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  AnswerDao answerDao;

  @Mock
  QuestionDao questionDao;

  AddAnswerController sut;

  @BeforeEach
  void setUp() {
    sut = new AddAnswerController(questionDao, answerDao);
  }

  @Test
  @DisplayName("응답을 저장한다.")
  void shouldSaveAnswer() throws ServletException, IOException {
    when(request.getParameter("writer")).thenReturn("사용자");
    when(request.getParameter("contents")).thenReturn("응답 컨텐츠입니다.");
    when(request.getParameter("questionId")).thenReturn("1");

    sut.handleRequest(request, response);

    verify(answerDao).insert(argThat(answer ->
      "사용자".equals(answer.getWriter()) &&
        "응답 컨텐츠입니다.".equals(answer.getContents()) &&
        answer.getQuestionId() == 1L
    ));
  }

  @Test
  @DisplayName("저장된 응답을 응답 바디에 전송한다.")
  void shouldRespondWithSavedAnswer() throws IOException, ServletException {
    Answer answer = anAnswer().build();
    when(request.getParameter("writer")).thenReturn(answer.getWriter());
    when(request.getParameter("contents")).thenReturn(answer.getContents());
    when(request.getParameter("questionId")).thenReturn(String.valueOf(answer.getQuestionId()));
    when(answerDao.insert(any(Answer.class))).thenReturn(answer);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("answer", answer);
  }

  @Test
  @DisplayName("답변을 추가하면 댓글의 수가 증가한다.")
  void shouldIncrementCountOfAnswers() throws ServletException, IOException {
    long questionId = 1L;
    when(request.getParameter("questionId")).thenReturn(String.valueOf(questionId));

    sut.handleRequest(request, response);

    verify(questionDao).incrementAnswerCount(questionId);
  }
}
