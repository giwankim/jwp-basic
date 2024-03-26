package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.next.model.User;
import org.junit.jupiter.api.AfterEach;
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

import static com.giwankim.next.Fixtures.aUser;
import static com.giwankim.next.controller.UserSessionUtils.SESSION_USER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserFormControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  HttpSession session;

  UpdateUserFormController sut;

  @BeforeEach
  void setUp() {
    sut = new UpdateUserFormController();
    Database.addUser(aUser().build());
  }

  @AfterEach
  void tearDown() {
    Database.deleteAll();
  }

  @Test
  void shouldReturnUpdateForm() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(aUser().build());

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("/user/updateForm.jsp");
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("user-does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }

  @Test
  void shouldThrowExceptionWhenUnauthorized() {
    User anotherUser = aUser().userId("different-user").build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(SESSION_USER_KEY)).thenReturn(anotherUser);

    assertThatExceptionOfType(UnauthorizedException.class)
      .isThrownBy(() -> sut.execute(request, response));
  }
}
