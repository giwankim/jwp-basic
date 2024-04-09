package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.ModelAndView;
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

import static com.giwankim.Fixtures.aQuestion;
import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
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
  HttpSession session;

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
    User user = aUser().build();
    Question question = aQuestion().build();
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(user);
    when(request.getParameter("title")).thenReturn(question.getTitle());
    when(request.getParameter("contents")).thenReturn(question.getContents());

    sut.handleRequest(request, response);

    verify(questionDao).insert(argThat(q ->
      user.getUserId().equals(q.getWriter()) &&
        question.getTitle().equals(q.getTitle()) &&
        question.getContents().equals(q.getContents())));
  }

  @Test
  @DisplayName("질문하기 생성에 성공한 후 질문 목록 페이지로 이동한다.")
  void shouldRedirect() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/"));
  }

  @Test
  @DisplayName("로그인하지 않은 경우 로그인 페이지로 이동한다.")
  void shouldRedirectToLoginFormWhenNotLoggedIn() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/user/loginForm"));
  }
}
