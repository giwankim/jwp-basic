package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.Controller;
import com.giwankim.core.mvc.JspView;
import com.giwankim.core.mvc.View;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController implements Controller {
  private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

  private final UserDao userDao;

  public CreateUserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public View handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = new User(
      request.getParameter("userId"),
      request.getParameter("password"),
      request.getParameter("name"),
      request.getParameter("email"));
    logger.debug("user : {}", user);
    userDao.insert(user);
    return JspView.from("redirect:/");
  }
}
