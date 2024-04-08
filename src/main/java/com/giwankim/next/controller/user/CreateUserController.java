package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

  private final UserDao userDao;

  public CreateUserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = new User(
      request.getParameter("userId"),
      request.getParameter("password"),
      request.getParameter("name"),
      request.getParameter("email"));
    logger.debug("user : {}", user);

    userDao.insert(user);
    return jspView("redirect:/");
  }
}
