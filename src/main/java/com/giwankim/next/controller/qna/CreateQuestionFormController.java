package com.giwankim.next.controller.qna;

import com.giwankim.core.mvc.AbstractController;
import com.giwankim.core.mvc.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateQuestionFormController extends AbstractController {
  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    return jspView("/qna/form.jsp");
  }
}
