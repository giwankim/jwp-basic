package com.giwankim.next.controller;

import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HomeControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  HomeController sut;

  UserDao userDao;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    userDao = mock(UserDao.class);
    sut = new HomeController(userDao);
  }

  @Test
  void shouldReturnView() throws ServletException, IOException {
    String view = sut.execute(request, response);
    assertThat(view).isEqualTo("home.jsp");
  }

  @Test
  void shouldAttachUsers() throws ServletException, IOException {
    List<User> users = List.of(aUser().userId("user1").build(), aUser().userId("user2").build());
    when(userDao.findAll())
      .thenReturn(users);

    sut.execute(request, response);

    verify(request).setAttribute("users", users);
  }
}