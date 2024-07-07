package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.UserDao;
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
import java.util.List;
import java.util.Map;

import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUserControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  @Mock
  UserDao userDao;

  ListUserController sut;

  @BeforeEach
  void setUp() {
    sut = new ListUserController(userDao);
  }

  @Test
  @DisplayName("유저 목록 페이지를 서빙한다.")
  void shouldForwardToUserListPage() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/user/list.jsp"));
  }

  @Test
  @DisplayName("유저 목록 속성을 세팅한다.")
  void shouldSetUsersAttribute() throws ServletException, IOException {
    List<User> users = List.of(
      aUser().userId("user1").build(),
      aUser().userId("user2").build());
    Map<String, List<User>> model = Map.of("users", users);
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());
    when(userDao.findAll()).thenReturn(users);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).isEqualTo(model);
  }

  @Test
  @DisplayName("유저가 로그인하지 않았으면 로그인 페이지로 이동한다.")
  void shouldRedirectToLoginPageIfUserIsNotLoggedIn() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/user/loginForm"));
  }
}
