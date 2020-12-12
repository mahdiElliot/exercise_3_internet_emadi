package Main.controller;

import Main.dbutil.UserDbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "user", urlPatterns = {"/user"})
public class UserViewController extends HttpServlet {
    private UserDbUtil userDbUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        userDbUtil = new UserDbUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            req.getRequestDispatcher("index.html").include(req, resp);
            return;
        }
        String username = (String) session.getAttribute("username");
        req.setAttribute("user", userDbUtil.getUser(username));
        req.getRequestDispatcher("userView.jsp").forward(req, resp);
    }
}