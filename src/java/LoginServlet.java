import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT name, email, password FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String dbName = rs.getString("name");

                if (password.equals(dbPassword)) {
                    // Create a session and store user data
                    HttpSession session = request.getSession();
                    session.setAttribute("userEmail", email);
                    session.setAttribute("userName", dbName);
                    session.setAttribute("isLoggedIn", true);
                    session.setMaxInactiveInterval(30 * 60); // 30 minutes

                    out.println("<script>alert('Login successful!'); window.location.href='home.html';</script>");
                } else {
                    out.println("<script>alert('Invalid Email or Password.'); window.location.href='login.html';</script>");
                }
            } else {
                out.println("<script>alert('Invalid Email or Password.'); window.location.href='login.html';</script>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<h3>Database Driver Not Found.</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("<h3>Database Error Occurred.</h3>");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

