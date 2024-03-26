package core.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ForwardController implements Controller {
  private final String forwardUrl;

  public ForwardController(String forwardUrl) {
    this.forwardUrl = Objects.requireNonNull(forwardUrl, "입력하신 URL이 null입니다.");
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    return forwardUrl;
  }
}
