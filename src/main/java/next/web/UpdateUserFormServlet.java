package next.web;

import core.db.Database;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.exception.UserNotFoundException;
import next.model.User;

@WebServlet(value = "/user/updateForm")
public class UpdateUserFormServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Optional<User> optionalUser = Database.findById(req.getParameter("userId"));
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("사용자가 존재하지 않습니다.");
    }
    req.setAttribute("user", optionalUser.get());
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/updateForm.jsp");
    requestDispatcher.forward(req, resp);
  }
}
