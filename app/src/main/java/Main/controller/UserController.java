package Main.controller;

import Main.dbutil.UserDbUtil;
import Main.model.Roles;
import Main.model.User;
import Main.utils.Password;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
class ToJsonUserList {
  ArrayList<User> userList;
  User self;
}

@WebServlet(name = "users", urlPatterns = {"/users/*"})
public class UserController extends HttpServlet {
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


    ToJsonUserList userList = new ToJsonUserList(userDbUtil.getUsers(), user.get());

    String json = new Gson().toJson(userList);


    resp.getWriter().println(json);

//    req.setAttribute("users", userDbUtil.getUsers());
//    req.setAttribute("self", user.get());
//    req.getRequestDispatcher("adminView.jsp").forward(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      register(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!isAdmin(req, resp)) return;



    String username = req.getPathInfo().split("/")[1];
    HttpSession session = req.getSession(false);

    String self = (String) session.getAttribute("username");

    if (username.compareTo("admin") == 0) {
      resp.setStatus(403);
      resp.getWriter().println("admin cannot be deleted!");
    }

    if (self.compareTo(username) == 0) {
      resp.setStatus(403);
      resp.getWriter().println("cant delete yourself");
    }

    boolean success = userDbUtil.deleteUser(username);

    if (success) {
      resp.setStatus(200);
      resp.getWriter().println(username + " deleted successfully");
    }
  }

  private void register(HttpServletRequest req, HttpServletResponse resp) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, ServletException {
    HttpSession session = req.getSession(false);

    if (session == null) {
      req.getRequestDispatcher("index.html").include(req, resp);
      return;
    }

    String loggedInUser = (String) session.getAttribute("username");
    Roles loggedInUserRole = (Roles) session.getAttribute("role");

    if (loggedInUser == null || loggedInUserRole != Roles.ADMIN) {
      resp.setStatus(403);
      resp.getWriter().println("YOU CANT ACCESS THIS PAGE!");
      return;
    }

    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String role = req.getParameter("role");
    String email = req.getParameter("email");

    Optional<User> foundUser = userDbUtil.getUser(username);

    if (foundUser.isPresent()) {
      resp.setStatus(400);
      resp.getWriter().println("User Already Exists");
      return;
    }

    User user = new User(username, Password.getEncodedPassword(password), Roles.valueOf(role), email);

    Optional<User> createdUser = userDbUtil.createUser(user);

    if(createdUser.isEmpty()) {
      resp.setStatus(400);
      resp.getWriter().println("Failed to create user");
      return;
    }

    resp.setStatus(200);
    resp.getWriter().println("User Created Successfully");
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
