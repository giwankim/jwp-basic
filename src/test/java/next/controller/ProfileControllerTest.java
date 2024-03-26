package next.controller;

import core.db.Database;
import next.exception.UserNotFoundException;
import next.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  ProfileController sut;

  @BeforeEach
  void setUp() {
    sut = new ProfileController();
    Database.addUser(new User("userId", "password", "name", "test@example.com"));
  }

  @AfterEach
  void tearDown() {
    Database.deleteAll();
  }

  @Test
  void shouldReturnProfileView() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");

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