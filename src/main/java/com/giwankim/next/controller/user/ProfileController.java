package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileController extends AbstractController {
  private final UserDao userDao;

  public ProfileController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    User user = userDao.findByUserId(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    return jspView("/user/profile.jsp")
      .addObject("user", user);
  }
}
