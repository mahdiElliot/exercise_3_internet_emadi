package Main.controller;

import Main.dbutil.UserDbUtil;
import Main.model.Roles;
import Main.model.User;
import Main.utils.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@WebServlet(name = "admin", urlPatterns = {"/admin"})
public class AdminViewController extends HttpServlet {
  private UserDbUtil userDbUtil;

  @Override
  public void init() throws ServletException {
    super.init();
    userDbUtil = new UserDbUtil();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!isAdmin(req, resp)) return;
    HttpSession session = req.getSession(false);
    String self = (String) session.getAttribute("username");

    Optional<User> user = userDbUtil.getUser(self);

    if (user.isEmpty()) {
      req.getRequestDispatcher("/index.html");
      return;
    }

    req.setAttribute("self", user.get());
    req.getRequestDispatcher("adminView.jsp").forward(req, resp);
  }

  private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);

    if (session == null) {
      req.getRequestDispatcher("index.html").include(req, resp);
      return false;
    }

    String username = (String) session.getAttribute("username");
    Roles role = (Roles) session.getAttribute("role");

    if (username == null || role != Roles.ADMIN) {
      resp.setStatus(403);
      resp.getWriter().println("YOU CAN'T ACCESS THIS PAGE!");
      return false;
    }
    return true;
  }

}
