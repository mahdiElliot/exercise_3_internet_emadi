package Main.controller;

import Main.dbutil.UserDbUtil;
import Main.utils.Password;
import Main.utils.Roles;
import Main.model.User;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Main.UserList.users;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private UserDbUtil userDbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        userDbUtil = new UserDbUtil();
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            login(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = findUser(username, password);
        if (user != null) {
            req.setAttribute("username", username);
            resp.setStatus(200);
            directUser(resp, user);
        } else {
            resp.setStatus(400);
            resp.getWriter().println("username or password is incorrect!");
        }
    }

    private void directUser(HttpServletResponse resp, User user) throws IOException {
        Roles role = user.getRole();
        if (role == Roles.ADMIN)
            resp.getWriter().println("/app/admin");
        else
            resp.getWriter().println("/app/user");
    }

    private User findUser(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] encodedPassword = Password.getEncodedPassword(password);
//    List<User> foundUsers = users.stream().
//            filter(
//                    p -> p.getUsername().equals(username) &&
//                            Arrays.equals(p.getPassword(), encodedPassword)
//            ).collect(Collectors.toList());
        return userDbUtil.getUser(username);
//    if (!foundUsers.isEmpty()) return foundUsers;
    }
}