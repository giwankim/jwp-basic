package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.Controller;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProfileController implements Controller {
  private final UserDao userDao;

  public ProfileController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    Optional<User> optionalUser = userDao.findByUserId(userId);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
    }
    request.setAttribute("user", optionalUser.get());
    return "/user/profile.jsp";
  }
}
