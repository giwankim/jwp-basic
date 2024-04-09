package com.giwankim.next.controller.user;

import com.giwankim.core.jdbc.ConnectionManager;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserControllerTest {
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  UserDao userDao;

  CreateUserController sut;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());
    sut = new CreateUserController(userDao);
  }

  @Test
  @DisplayName("루트 경로로 이동한다.")
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("name")).thenReturn("name");
    when(request.getParameter("email")).thenReturn("email");

    ModelAndView mv = sut.handleRequest(request, response);

    assertThat(mv.getView()).isEqualTo(JspView.from("redirect:/"));
  }

  @Test
  @DisplayName("사용자를 저장한다.")
  void shouldPersistUser() throws ServletException, IOException {
    User user = aUser().build();
    when(request.getParameter("userId")).thenReturn(user.getUserId());
    when(request.getParameter("password")).thenReturn(user.getPassword());
    when(request.getParameter("name")).thenReturn(user.getName());
    when(request.getParameter("email")).thenReturn(user.getEmail());

    sut.handleRequest(request, response);

    verify(userDao).insert(user);
  }
}