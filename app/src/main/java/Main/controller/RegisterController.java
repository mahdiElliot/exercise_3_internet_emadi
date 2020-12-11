package Main.controller;

import Main.model.Roles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "register", urlPatterns = {"/user/add"})
public class RegisterController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session= req.getSession(false);

    if (session == null) {
      req.getRequestDispatcher("index.html").include(req,resp);
      return;
    }

    String username = (String) session.getAttribute("username");
    Roles role = (Roles) session.getAttribute("role");

    if (username == null || role != Roles.ADMIN) {
      resp.setStatus(403);
      resp.getWriter().println("YOU CANT ACCESS THIS PAGE!");
      return;
    }



    req.getRequestDispatcher("adminView.jsp").forward(req, resp);

  }
}