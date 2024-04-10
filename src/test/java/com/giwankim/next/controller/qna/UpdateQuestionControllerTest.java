package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UnauthorizedException;
import com.giwankim.next.dao.QuestionDao;
import com.giwankim.next.model.Question;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.Fixtures.aQuestion;
import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateQuestionControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  QuestionDao questionDao;

  UpdateQuestionController sut;

  @BeforeEach
  void setUp() {
    sut = new UpdateQuestionController(questionDao);
  }

  @Test
  @DisplayName("질문을 수정한다.")
  void shouldUpdate() throws ServletException, IOException {
    User user = aUser().build();
    Question question = aQuestion()
      .writer(user.getUserId())
      .build();
    long questionId = question.getQuestionId();
    Question updateQuestion = aQuestion()
      .writer(user.getUserId())
      .title("new-title")
      .contents("new-content")
      .build();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(Long.toString(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));
    when(request.getParameter("title")).thenReturn("new-title");
    when(request.getParameter("contents")).thenReturn("new-content");

    sut.handleRequest(request, response);

    verify(questionDao).update(updateQuestion);
  }

  @Test
  @DisplayName("질문을 찾지 못하면 예외를 던진다.")
  void shouldThrowExceptionWhenQuestionNotFound() {
    User user = aUser().build();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(Long.toString(99L));
    when(questionDao.findById(99L)).thenReturn(Optional.empty());

    assertThatExceptionOfType(QuestionNotFoundException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }

  @Test
  @DisplayName("루트 페이지로 이동한다.")
  void shouldRedirectToHome() throws ServletException, IOException {
    User user = aUser().build();
    Question question = aQuestion()
      .writer(user.getUserId())
      .build();
    long questionId = question.getQuestionId();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(Long.toString(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/"));
  }

  @Test
  @DisplayName("사용자가 로그인하지 않았으면 로그인 페이지로 이동한다.")
  void shouldRedirectToLoginWhenUserNotLoggedIn() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/user/loginForm"));
  }

  @Test
  @DisplayName("사용자가 질문 작성자가 아니면 예외를 던진다.")
  void shouldThrowExceptionWhenWriterIsNotLoggedInUser() {
    User user = aUser().build();
    Question question = aQuestion().writer("different-user").build();
    long questionId = question.getQuestionId();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(Long.toString(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }
}
