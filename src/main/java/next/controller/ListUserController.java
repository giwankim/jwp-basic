package next.controller;

import core.db.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/list")
public class ListUserController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    if (!UserSessionUtils.isLoggedIn(req.getSession())) {
      resp.sendRedirect("/user/loginForm");
      return;
    }
    req.setAttribute("users", Database.findAll());
    RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
    rd.forward(req, resp);
  }
}
