import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/forgot")
public class ForgotServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve form data
        //String username = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("new-Password");
        String confirmPassword = request.getParameter("confirm-Password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            out.println("<h3>Passwords do not match. Please try again.</h3>");
            return;
        }
        
        // Initialize database connection
         Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Establish connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insert user data into the database
            String sql = "Update users set password = ? where email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            //pstmt.setString(3, password);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                //response.sendRedirect("login.html");
                out.println("<script type=\'text/javascript\'>");
                out.println("alert('Password Changed Successfully!');");
                out.println("window.location.href='login.html'");
                out.println("</script>");
                
                
            } else {
                out.println("<h3>Error in changing password. Please try again.</h3>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<h3>Database driver not found.</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("<h3>Database error occurred.</h3>");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
