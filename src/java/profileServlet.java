/*import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/profile")
public class profileServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get logged-in user email from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            out.println("<script type='text/javascript'>");
            out.println("alert('Please log in first!');");
            out.println("window.location.href='login.html';");
            out.println("</script>");
            return;
        }
        String email = (String) session.getAttribute("email");

        // Database variables
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Query to get user details
            String sql = "SELECT name, email FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve user details
                String name = rs.getString("name");

                // Set attributes to pass to the JSP/HTML
                request.setAttribute("name", name);
                request.setAttribute("email", email);

                // Forward to profile.html (use JSP for dynamic content)
                request.getRequestDispatcher("profile.html").forward(request, response);

            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('User details not found!');");
                out.println("window.location.href='login.html';");
                out.println("</script>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h3>Database driver not found.</h3>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Database error occurred.</h3>");
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
}*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class profileServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get user email from cookies
        String email = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userEmail".equals(cookie.getName())) {
                    email = cookie.getValue();
                }
            }
        }

        if (email == null) {
            // If no email found in cookies, redirect to login page
            response.sendRedirect("login.html");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Query to fetch user details
            String sql = "SELECT name, email FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Extract user details
                String name = rs.getString("name");

                // Forward data to profile.html
                request.setAttribute("name", name);
                request.setAttribute("email", email);

                request.getRequestDispatcher("profile.html").forward(request, response);
            } else {
                out.println("<h3>No user found with email: " + email + "</h3>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h3>Database driver not found.</h3>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Database error occurred.</h3>");
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

