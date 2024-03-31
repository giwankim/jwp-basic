package next.controller;

import core.db.Database;
import next.exception.UserNotFoundException;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = {"/user/update", "/user/updateForm"})
public class UpdateUserController extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String userId = req.getParameter("userId");
    User user = Database.findById(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
      throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
    }
    req.setAttribute("user", user);
    RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
    rd.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String userId = req.getParameter("userId");
    User user = Database.findById(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
      throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
    }
    User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
    user.update(updateUser);
    logger.debug("update user : {}", updateUser);
    resp.sendRedirect("/");
  }
}
