package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
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

import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
  void shouldForwardToUserListPage() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());

    View actual = sut.handleRequest(request, response);

    assertThat(actual).isEqualTo(JspView.from("/user/list.jsp"));
  }

  @Test
  void shouldSetUsersAttribute() throws ServletException, IOException {
    List<User> users = List.of(
      aUser().userId("user1").build(),
      aUser().userId("user2").build());
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());
    when(userDao.findAll()).thenReturn(users);

    sut.handleRequest(request, response);

    verify(request).setAttribute("users", users);
  }

  @Test
  void shouldRedirectToLoginPageIfUserIsNotLoggedIn() throws ServletException, IOException {
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(null);

    View actual = sut.handleRequest(request, response);

    assertThat(actual).isEqualTo(JspView.from("redirect:/user/loginForm"));
  }
}