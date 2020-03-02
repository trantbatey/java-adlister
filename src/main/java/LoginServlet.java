import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // handle get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Forward GET request to JSP regardless of parameter values.
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // handle post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // redirect to profile if use is admin with correct password
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && username.equals("admin") && password != null && password.equals("password")) {
            response.sendRedirect("./profile");
        } else {

            // Send back to login if the username or password are incorrect
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
