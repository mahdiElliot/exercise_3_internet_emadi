package Main.controller;

import Main.dbutil.UserDbUtil;
import Main.model.Roles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        req.setAttribute("users", userDbUtil.getUsers());
        req.getRequestDispatcher("adminView.jsp").forward(req, resp);
    }
}