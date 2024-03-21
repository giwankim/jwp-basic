package next.controller;

import core.db.Database;
import next.exception.UserNotFoundException;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/user/profile")
public class ProfileController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String userId = req.getParameter("userId");
    Optional<User> optionalUser = Database.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
    }
    User user = optionalUser.get();
    req.setAttribute("user", user);
    RequestDispatcher rd = req.getRequestDispatcher("/user/profile.jsp");
    rd.forward(req, resp);
  }
}
