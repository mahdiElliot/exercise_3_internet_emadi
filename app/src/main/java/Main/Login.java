package Main;

import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Main.UserList.users;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    byte[] encodedPassword = Password.getEncodedPassword(password);

    // Create Admin moderator
    users.add(new Person("admin", Password.getEncodedPassword("admin"), Roles.ADMIN));

    List<Person> foundUsers = users.stream().filter(p -> p.getUsername().equals(username) && Arrays.equals(p.getPassword(), encodedPassword)).collect(Collectors.toList());

    boolean isUserFound = foundUsers.size() > 0;

    req.setAttribute("username", username);

    if (isUserFound) {
      Roles role = foundUsers.get(0).getRole();

      req.setAttribute("role", role.toString());

      if (role == Roles.ADMIN) {
        resp.sendRedirect("/app/admin");
      } else {
        resp.sendRedirect("/app/user");
      }

    } else {
      req.getRequestDispatcher("LoginFail.jsp").forward(req, resp);
    }
  }
}