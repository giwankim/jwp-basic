package next.web;

import core.db.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(CreateUserServlet.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User user = new User(req.getParameter("userId"), req.getParameter("password"),
        req.getParameter("email"), req.getParameter("name"));
    Database.addUser(user);
    logger.debug("user : {}", user);
    resp.sendRedirect("/user/list");
  }
}
