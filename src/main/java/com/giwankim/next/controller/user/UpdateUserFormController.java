package com.giwankim.next.controller.user;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;
import com.giwankim.next.controller.UnauthorizedException;
import com.giwankim.next.controller.UserSessionUtils;
import com.giwankim.next.dao.UserDao;
import com.giwankim.next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserFormController extends AbstractController {
  private final UserDao userDao;

  public UpdateUserFormController(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    User user = userDao.findByUserId(userId)
      .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
      throw new UnauthorizedException("다른 사용자의 정보를 수정할 수 없습니다.");
    }
    request.setAttribute("user", user);
    return jspView("/user/updateForm.jsp")
      .addObject("user", user);
  }
}
