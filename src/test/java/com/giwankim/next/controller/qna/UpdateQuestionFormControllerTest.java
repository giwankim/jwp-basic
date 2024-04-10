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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateQuestionFormControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  QuestionDao questionDao;

  UpdateQuestionFormController sut;

  @BeforeEach
  void setUp() {
    sut = new UpdateQuestionFormController(questionDao);
  }

  @Test
  @DisplayName("질문 수정 페이지를 서빙한다.")
  void shouldServeQuestionUpdateForm() throws ServletException, IOException {
    String userId = "test-user";
    User user = aUser().userId(userId).build();
    Question question = aQuestion().writer(userId).build();
    long questionId = question.getQuestionId();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(String.valueOf(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/qna/updateForm.jsp"));
  }

  @Test
  @DisplayName("유저가 로그인하지 않았으면 로그인 페이지로 이동한다.")
  void shouldRedirectToLoginPageWhenUserNotLoggedIn() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/user/loginForm"));
  }

  @Test
  @DisplayName("질문을 UI 모델에 추가한다.")
  void shouldAttachQuestionToModel() throws ServletException, IOException {
    String userId = "test-user";
    User user = aUser().userId(userId).build();
    Question question = aQuestion().writer(userId).build();
    long questionId = question.getQuestionId();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(String.valueOf(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("question", question);
  }

  @Test
  @DisplayName("질문을 못찾을 경우 예외를 던진다.")
  void shouldThrowExceptionWhenQuestionNotFound() {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());
    when(request.getParameter("questionId")).thenReturn("99");

    assertThatExceptionOfType(QuestionNotFoundException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }

  @Test
  @DisplayName("글쓴이와 로그인 사용자가 다른 경우 예외를 던진다.")
  void shouldThrowExceptionWhenWriterIsDifferentFromLoggedInUser() {
    User user = aUser().build();
    User anotherUser = aUser().userId("another").build();
    Question question = aQuestion().writer(anotherUser.getUserId()).build();
    long questionId = question.getQuestionId();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("questionId")).thenReturn(String.valueOf(questionId));
    when(questionDao.findById(questionId)).thenReturn(Optional.of(question));

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }
}
