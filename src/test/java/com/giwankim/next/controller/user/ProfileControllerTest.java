package com.giwankim.next.controller.user;

import com.giwankim.next.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfileControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  UserDao userDao;

  ProfileController sut;


  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    userDao = mock(UserDao.class);
    sut = new ProfileController(userDao);
  }

  @Test
  void shouldReturnProfileView() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    String actual = sut.execute(request, response);

    assertThat(actual).isEqualTo("/user/profile.jsp");
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("user-does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }
}