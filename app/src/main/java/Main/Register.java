package Main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Main.UserList.users;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String fname = req.getParameter("first_name");
      String lname = req.getParameter("last_name");

    if (fname != null && lname != null) {
      users.add(new Person(fname, lname));
    }

    req.setAttribute("fname", fname);
    req.setAttribute("lname", lname);
    req.getRequestDispatcher("registerCompleted.jsp").forward(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.getWriter().println("test");
  }
}