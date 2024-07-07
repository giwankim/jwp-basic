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
import java.io.IOException;
import java.util.Optional;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  UserDao userDao;

  ProfileController sut;


  @BeforeEach
  void setUp() {
    sut = new ProfileController(userDao);
  }

  @Test
  @DisplayName("프로파일 페이지를 서빙한다.")
  void shouldReturnProfileView() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(aUser().build()));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("/user/profile.jsp"));
  }

  @Test
  @DisplayName("User 속성을 세팅한다.")
  void shouldSetUserAttribute() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn("userId");
    when(userDao.findByUserId("userId")).thenReturn(Optional.of(user));

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getModel()).containsEntry("user", user);
  }

  @Test
  @DisplayName("User를 찾지 못하면 exception을 던진다.")
  void shouldThrowExceptionWhenUserNotFound() {
    when(request.getParameter("userId")).thenReturn("user-does-not-exist");

    assertThatExceptionOfType(UserNotFoundException.class)
      .isThrownBy(() -> sut.handleRequest(request, response));
  }
}