package next.controller;

import core.db.Database;
import core.mvc.Controller;
import next.exception.UserNotFoundException;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProfileController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Optional<User> optionalUser = Database.findById(request.getParameter("userId"));
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
    }
    request.setAttribute("user", optionalUser.get());
    return "/user/profile.jsp";
  }
}
