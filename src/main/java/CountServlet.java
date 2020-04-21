
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CountServlet", urlPatterns = "/count")
public class CountServlet extends HttpServlet {

    // set static counter
    private static int count = 0;

    // Server the GET request to say hello
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String reset = req.getParameter("reset");
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        count++;
        if (reset != null && reset.toLowerCase().equals("true")) {
            count = 1;
            pw.println("<h1>The count has been reset!</h1>");
        }
        pw.println("<h1>The count is: " + count + "</h1>");
    }
}
