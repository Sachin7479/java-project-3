import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentID = request.getParameter("studentID");
        String date = request.getParameter("date");
        String status = request.getParameter("status");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancedb", "root", "password");
            
            String query = "INSERT INTO Attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentID);
            pstmt.setString(2, date);
            pstmt.setString(3, status);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                out.println("<html><body>");
                out.println("<h2>Attendance Recorded Successfully!</h2>");
                out.println("<p>Student ID: " + studentID + "</p>");
                out.println("<p>Date: " + date + "</p>");
                out.println("<p>Status: " + status + "</p>");
                out.println("<a href='AttendanceForm.jsp'>Back to Form</a>");
                out.println("</body></html>");
            } else {
                out.println("<h2>Error: Failed to record attendance</h2>");
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
