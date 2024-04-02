package com.giwankim.next.controller.user;

import com.giwankim.core.jdbc.ConnectionManager;
import com.giwankim.next.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.giwankim.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateUserControllerTest {
  HttpServletRequest request;

  HttpServletResponse response;

  CreateUserController sut;

  UserDao userDao;

  @BeforeEach
  void setUp() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    populator.addScript(new ClassPathResource("data.sql"));
    DatabasePopulatorUtils.execute(populator, ConnectionManager.getDatasource());

    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    userDao = mock(UserDao.class);
    sut = new CreateUserController(userDao);
  }

  @Test
  void shouldRedirectToRoot() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("name")).thenReturn("name");
    when(request.getParameter("email")).thenReturn("email");

    String view = sut.execute(request, response);

    assertThat(view).isEqualTo("redirect:/");
  }

  @Test
  void shouldPersistUser() throws ServletException, IOException {
    when(request.getParameter("userId")).thenReturn("userId");
    when(request.getParameter("password")).thenReturn("password");
    when(request.getParameter("name")).thenReturn("name");
    when(request.getParameter("email")).thenReturn("email");

    sut.execute(request, response);

    verify(userDao).insert(aUser().build());
  }
}