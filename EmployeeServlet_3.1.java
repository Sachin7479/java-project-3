import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Database connection parameters
    private static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String empID = request.getParameter("empID");
        
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            out.println("<html><head><title>Employee Records</title>");
            out.println("<style>table{border-collapse:collapse;} th,td{border:1px solid black;padding:8px;}");
            out.println("</style></head><body>");
            out.println("<h2>Employee Records</h2>");
            
            if (empID != null && !empID.trim().isEmpty()) {
                // Search by Employee ID
                String query = "SELECT * FROM employees WHERE empID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, empID);
                ResultSet rs = stmt.executeQuery();
                
                out.println("<h3>Search Results:</h3>");
                out.println("<table border='1'><tr><th>Employee ID</th><th>Name</th><th>Salary</th></tr>");
                
                if (rs.next()) {
                    out.println("<tr><td>" + rs.getString("empID") + "</td><td>" + 
                               rs.getString("name") + "</td><td>" + rs.getString("salary") + "</td></tr>");
                } else {
                    out.println("<tr><td colspan='3'>No record found</td></tr>");
                }
                out.println("</table>");
                rs.close();
                stmt.close();
            } else {
                // Display all employees
                String query = "SELECT * FROM employees";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                out.println("<h3>All Employees:</h3>");
                out.println("<table border='1'><tr><th>Employee ID</th><th>Name</th><th>Salary</th></tr>");
                
                while (rs.next()) {
                    out.println("<tr><td>" + rs.getString("empID") + "</td><td>" + 
                               rs.getString("name") + "</td><td>" + rs.getString("salary") + "</td></tr>");
                }
                out.println("</table>");
                rs.close();
                stmt.close();
            }
            
            // Display search form
            out.println("<br><h3>Search Employee by ID:</h3>");
            out.println("<form method='GET'>");
            out.println("<input type='text' name='empID' placeholder='Enter Employee ID'>");
            out.println("<input type='submit' value='Search'>");
            out.println("</form>");
            
            conn.close();
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
