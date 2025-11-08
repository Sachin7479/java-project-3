import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get parameters from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Set response content type
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Simple validation - hardcoded credentials for demonstration
        if (username != null && password != null && 
            username.equals("admin") && password.equals("password123")) {
            // Valid credentials
            out.println("<html>");
            out.println("<head><title>Login Success</title></head>");
            out.println("<body>");
            out.println("<h1>Welcome, " + username + "!</h1>");
            out.println("<p>You have successfully logged in.</p>");
            out.println("</body>");
            out.println("</html>");
        } else {
            // Invalid credentials
            out.println("<html>");
            out.println("<head><title>Login Failed</title></head>");
            out.println("<body>");
            out.println("<h1>Login Failed!</h1>");
            out.println("<p>Invalid username or password.</p>");
            out.println("<a href='LoginForm.html'>Try again</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
