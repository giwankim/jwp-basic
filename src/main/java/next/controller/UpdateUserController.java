package next.controller;

import core.db.Database;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.exception.UserNotFoundException;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value = "/user/update")
public class UpdateUserController extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    String userId = req.getParameter("userId");
    Optional<User> optionalUser = Database.findById(userId);
    if (optionalUser.isEmpty()) {
      resp.sendError(404, "사용자 " + userId + "가 존재하지 않습니다.");
    }
    User user = optionalUser.get();
    User updateUser = new User(
      req.getParameter("userId"),
      req.getParameter("password"),
      req.getParameter("name"),
      req.getParameter("email"));
    user.update(updateUser);
    logger.debug("update user : {}", updateUser);
    resp.sendRedirect("/");
  }
}
