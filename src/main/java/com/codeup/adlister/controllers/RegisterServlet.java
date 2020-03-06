package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Pattern pattern = Pattern.compile("[A-Za-z_][A-Za-z0-9_]+");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ensure the submitted information is valid
        request.setAttribute("error", null);
        if (!request.getParameter("password").equals(request.getParameter("confirm_password"))) {
            request.setAttribute("error", "Password confirmation failed.");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                    .forward(request, response);
        } else if (!vaildateUsername(request.getParameter("username"))) {
            request.setAttribute("error", "Invalid username");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                    .forward(request, response);
        } else if (!vaildateEmail(request.getParameter("email"))) {
            request.setAttribute("error", "Invalid email address");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                    .forward(request, response);
        } else if (!vaildatePassword(request.getParameter("password"))) {
            request.setAttribute("error", "Invalid password");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                    .forward(request, response);
        } else {

            // create a new user based off of the submitted information
            User user = new User(
                    DaoFactory.getUsersDao().getMaxId() + 1,
                    request.getParameter("username"),
                    request.getParameter("email"),
                    request.getParameter("password")
            );
            DaoFactory.getUsersDao().insert(user);

            // if a user was successfully created, send them to their profile
            request.getSession().invalidate();
            request.getSession().setAttribute("user", user);
            response.sendRedirect("./profile");
        }
    }

    // validate username
    private static boolean vaildateUsername(String username) {
        if (username == null) return false;
        if (username.length() < 4 || username.length() > 20) return false;
        if (!pattern.matcher(username).matches()) return false;
        return true;
    }

    // validate email
    private static boolean vaildateEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    // validate password
    private static boolean vaildatePassword(String password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpecial = false;
        boolean hasDigit = false;
        boolean result = true;

        if (password == null) return false;
        if (password.length() < 4 || password.length() > 20) return false;
        for(int i =0; i<password.length(); i++){
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (c>=33&&c<=46||c==64) {
                hasSpecial = true;
            } else {
                result = false;
                break;
            }
        }
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) result = false;
        return result;
    }
}
