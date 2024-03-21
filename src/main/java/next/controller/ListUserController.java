package next.controller;

import core.db.Database;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/user/list")
public class ListUserController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.setAttribute("users", Database.findAll());
    RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
    rd.forward(req, resp);
  }
}
