package com.giwankim.next.controller.user;

import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.giwankim.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListUserControllerTest {
  MockHttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  UserDao userDao;

  ListUserController sut;


  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    session = new MockHttpSession();
    userDao = mock(UserDao.class);
    sut = new ListUserController(userDao);
  }

  @Test
  void shouldForwardToUserListPage() throws ServletException, IOException {
    session.setAttribute(SESSION_USER_KEY, aUser().build());
    request.setSession(session);

    String actual = sut.execute(request, response);

    assertThat(actual).isEqualTo("/user/list.jsp");
  }

  @Test
  void shouldSetUsersAttribute() throws ServletException, IOException {
    session.setAttribute(SESSION_USER_KEY, aUser().build());
    request.setSession(session);
    List<User> users = List.of(aUser().userId("user1").build(), aUser().userId("user2").build());
    when(userDao.findAll()).thenReturn(users);

    sut.execute(request, response);

    assertThat(request.getAttribute("users")).isEqualTo(users);
  }

  @Test
  void shouldRedirectToLoginPageIfUserIsNotLoggedIn() throws ServletException, IOException {
    String actual = sut.execute(request, response);

    assertThat(actual).isEqualTo("redirect:/user/loginForm");
  }
}