package com.giwankim.next.controller;

import com.giwankim.core.db.Database;
import com.giwankim.core.mvc.Controller;
import com.giwankim.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController implements Controller {
  private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = new User(
      request.getParameter("userId"),
      request.getParameter("password"),
      request.getParameter("name"),
      request.getParameter("email"));
    Database.addUser(user);
    logger.debug("user : {}", user);
    return "redirect:/";
  }
}
