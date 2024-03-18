package next.web;

import core.db.Database;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(ListUserServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setAttribute("users", Database.findAll());
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/list.jsp");
    requestDispatcher.forward(req, resp);
  }
}
