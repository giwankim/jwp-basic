package next.controller;

import core.db.Database;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/create")
public class CreateUserController extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    User user = new User(
      req.getParameter("userId"),
      req.getParameter("password"),
      req.getParameter("name"),
      req.getParameter("email"));
    Database.addUser(user);
    logger.debug("user : {}", user);
    resp.sendRedirect("/");
  }
}
