import java.io.IOException;
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false); // Don't create a new session
        if (session == null || session.getAttribute("userEmail") == null) {
            // No session or not logged in
            response.sendRedirect("login.html");
            return;
        }

        String name = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("userEmail");

        // Display HTML with user details
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Profile</title><link rel='stylesheet' href='css/profilestyle.css'>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap' rel='stylesheet'>");
        out.println("</head><body>");
        out.println("<div class='profile-container'>");
        out.println("<div class='profile-header'><a href='home.html' class='back'><img src='images/back1.png' width='40'></a><h1>User Profile</h1></div>");
        out.println("<div class='profile-content'><div class='profile-card'>");
        out.println("<p><strong>Name:</strong> " + name + "</p>");
        out.println("<p><strong>Email:</strong> " + email + "</p>");
        out.println("</div></div></div>");
        out.println("</body></html>");
    }
}


//@WebServlet("/profile")
//public class profileServlet extends HttpServlet {
//
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static final String DB_USER = "system";
//    private static final String DB_PASSWORD = "system";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("email") == null) {
//            response.sendRedirect("login.html");
//            return;
//        }
//
//        String email = (String) session.getAttribute("email");
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            String sql = "SELECT name FROM users WHERE email = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                String name = rs.getString("name");
//
//                out.println("<!DOCTYPE html>");
//                out.println("<html lang='en'>");
//                out.println("<head>");
//                out.println("<meta charset='UTF-8'>");
//                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
//                out.println("<title>User Profile</title>");
//                out.println("<link rel='stylesheet' href='css/profilestyle.css'>");
//                out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap' rel='stylesheet'>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<div class='profile-container'>");
//                out.println("<div class='profile-header'>");
//                out.println("<a href='home.html' class='back'><img src='images/back1.png' alt='back' width='40'></a>");
//                out.println("<h1>User Profile</h1>");
//                out.println("</div>");
//                out.println("<div class='profile-content'>");
//                out.println("<div class='profile-card'>");
//                out.println("<p><strong>Name:</strong> " + name + "</p>");
//                out.println("<p><strong>Email:</strong> " + email + "</p>");
//                out.println("</div>");
//                out.println("</div>");
//                out.println("</div>");
//                out.println("</body>");
//                out.println("</html>");
//            } else {
//                out.println("<h3>User not found!</h3>");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            out.println("<h3>Error occurred while fetching profile data.</h3>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}


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

//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/profile")
//public class profileServlet extends HttpServlet {
//
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static final String DB_USER = "system";
//    private static final String DB_PASSWORD = "system";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        // Get user email from cookies
//        String email = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("userEmail".equals(cookie.getName())) {
//                    email = cookie.getValue();
//                }
//            }
//        }
//
//        if (email == null) {
//            // If no email found in cookies, redirect to login page
//            response.sendRedirect("login.html");
//            return;
//        }
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            // Load Oracle JDBC Driver
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            // Connect to the database
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//            // Query to fetch user details
//            String sql = "SELECT name, email FROM users WHERE email = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                // Extract user details
//                String name = rs.getString("name");
//
//                // Forward data to profile.html
//                request.setAttribute("name", name);
//                request.setAttribute("email", email);
//
//                request.getRequestDispatcher("profile.html").forward(request, response);
//            } else {
//                out.println("<h3>No user found with email: " + email + "</h3>");
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            out.println("<h3>Database driver not found.</h3>");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            out.println("<h3>Database error occurred.</h3>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}


//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/profile")
//public class profileServlet extends HttpServlet {
//
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static final String DB_USER = "system";
//    private static final String DB_PASSWORD = "system";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        // Get session and retrieve email and username
//        HttpSession session = request.getSession(false); // false = do not create new if it doesn't exist
//        if (session == null || session.getAttribute("email") == null) {
//            // Not logged in
//            response.sendRedirect("login.html");
//            return;
//        }
//
//        String email = (String) session.getAttribute("email");
//        String username = (String) session.getAttribute("username");
//
//        // Display user details
//        out.println("<html><head><title>User Profile</title></head><body>");
//        out.println("<h2>Welcome, " + username + "!</h2>");
//        out.println("<p>Email: " + email + "</p>");
//        out.println("<a href='logout'>Logout</a>");
//        out.println("</body></html>");
//    }
//}
