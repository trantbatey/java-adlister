package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("./profile");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isAttemptValid = false;
        User user = DaoFactory.getUsersDao().findByUsername(username);
        if (user != null && password != null && BCrypt.checkpw(password, user.getPassword())) {
            isAttemptValid = true;
        }

        if (isAttemptValid) {
            // store the logged in user object in the session, instead of just the username
            request.getSession().setAttribute("user", user);
            response.sendRedirect("./profile");
            return;
        } else {
            response.sendRedirect("./login");
            return;
        }
    }
}
