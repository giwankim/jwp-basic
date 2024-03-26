package next.controller;

import core.db.Database;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/user/login")
public class LoginController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String userId = req.getParameter("userId");
    String password = req.getParameter("password");
    Optional<User> optionalUser = Database.findById(userId);
    if (optionalUser.isEmpty()) {
      req.setAttribute("loginFailed", true);
      forward(req, resp);
      return;
    }

    User user = optionalUser.get();
    if (user.comparePasswords(password)) {
      HttpSession session = req.getSession();
      session.setAttribute(UserSessionUtils.SESSION_USER_KEY, user);
      resp.sendRedirect("/");
      return;
    }
    req.setAttribute("loginFailed", true);
    forward(req, resp);
  }

  private static void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
    rd.forward(req, resp);
  }
}
