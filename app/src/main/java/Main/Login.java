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

    List<Person> foundUsers = users.stream().filter(p -> p.getUsername().equals(username) && Arrays.equals(p.getPassword(), encodedPassword)).collect(Collectors.toList());

    boolean isUserFound = foundUsers.size() > 0;

    req.setAttribute("username", username);

    if (isUserFound) {
      Roles role = foundUsers.get(0).getRole();

      resp.setStatus(200);

      if (role == Roles.ADMIN) {
        resp.getWriter().println("/app/admin");
      } else {
        resp.getWriter().println("/app/user");
      }

    } else {
      resp.setStatus(400);
      resp.getWriter().println("username or password is incorrect!");
    }
  }
}