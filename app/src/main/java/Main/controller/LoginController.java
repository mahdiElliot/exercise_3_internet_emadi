package Main.controller;

import Main.dbutil.UserDbUtil;
import Main.utils.Password;
import Main.model.Roles;
import Main.model.User;
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
import java.util.Arrays;

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
            HttpSession session = req.getSession();

            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());

            req.setAttribute("username", username);
            resp.setStatus(200);


            if (user.getRole() == Roles.ADMIN) {
                resp.getWriter().println("/app/admin");
            } else {
                resp.getWriter().println("/app/user");
            }

        } else {
            resp.setStatus(400);
            resp.getWriter().println("username or password is incorrect!");
        }
    }

    private User findUser(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String encodedPassword = Password.getEncodedPassword(password);
        User user = userDbUtil.getUser(username);

        System.out.println(encodedPassword);
        System.out.println(user.getPassword());

        if (encodedPassword.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}