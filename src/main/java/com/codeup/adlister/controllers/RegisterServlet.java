package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;

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

        // *** ensure the submitted information is valid ***
        boolean userInfoIsInvalid = false;
        request.setAttribute("error", null);
        String redirectPath = "/WEB-INF/users/register.jsp";
        if (!userRegistrationDataIsInvalid(request)) {
            request.getRequestDispatcher(redirectPath).forward(request, response);
            return;
        }

        // create a new user based off of the user registration data submitted
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

    // validate user registration information
    private static boolean userRegistrationDataIsInvalid(HttpServletRequest request) {

        // *** ensure the submitted information is valid ***
        boolean userInfoIsInvalid = false;
        request.setAttribute("error", null);

        // check username meets criteria
        if (!isUsernameValid(request.getParameter("username"))) {
            request.setAttribute("error", "Invalid username");
            return false;
        }

        // Check for valid email
        if (!isEmailValid(request.getParameter("email"))) {
            request.setAttribute("error", "Invalid email address");
            return false;
        }

        // validate password
        if (!isPasswordValid(request.getParameter("password"))) {
            request.setAttribute("error", "Invalid password");
            return false;
        }

        // Confirm password
        if (!request.getParameter("password").equals(request.getParameter("confirm_password"))) {
            request.setAttribute("error", "Password confirmation failed.");
            return false;
        }

        return true;
    }

    // validate username
    private static boolean isUsernameValid(String username) {
        if (username == null) return false;
        if (username.length() < 4 || username.length() > 20) return false;
        if (!pattern.matcher(username).matches()) return false;
        return true;
    }

    // validate email
    private static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    // validate password
    private static boolean isPasswordValid(String password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpecial = false;
        boolean hasDigit = false;
        boolean result = true;

        if (password == null) return false;
        if (password.length() < 4 || password.length() > 20) return false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (c >= 33 && c <= 46 || c == 64) {
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
