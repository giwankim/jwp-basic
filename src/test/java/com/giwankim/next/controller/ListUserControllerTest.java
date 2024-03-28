package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ListUserControllerTest {
  MockHttpServletRequest request;

  HttpServletResponse response;

  HttpSession session;

  ListUserController sut;

  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    session = new MockHttpSession();
    sut = new ListUserController();
    Database.addUser(new User("user1", "password1", "name1", "test1@example.com"));
    Database.addUser(new User("user2", "password2", "name2", "test2@example.com"));
  }

  @AfterEach
  void tearDown() {
    Database.deleteAll();
  }

  @Test
  void shouldForwardToUserListPage() throws ServletException, IOException {
    session.setAttribute(SESSION_USER_KEY, new User("userId", "password", "name", "test@example.com"));
    request.setSession(session);

    String actual = sut.execute(request, response);

    assertThat(actual).isEqualTo("/user/list.jsp");
  }

  @Test
  void shouldSetUsersAttribute() throws ServletException, IOException {
    session.setAttribute(SESSION_USER_KEY, new User("userId", "password", "name", "test@example.com"));
    request.setSession(session);

    sut.execute(request, response);

    assertThat(request.getAttribute("users")).isEqualTo(Database.findAll());
  }

  @Test
  void shouldRedirectToLoginPageIfUserIsNotLoggedIn() throws ServletException, IOException {
    String actual = sut.execute(request, response);
    assertThat(actual).isEqualTo("redirect:/user/loginForm");
  }
}