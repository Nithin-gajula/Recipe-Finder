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

//        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
//            out.println("<script>alert('Email and Password are required.'); window.location.href='login.html';</script>");
//            return;
//        }

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


//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.*;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static final String DB_USER = "system";
//    private static final String DB_PASSWORD = "system";
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // Retrieve form data
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            // Load Oracle JDBC Driver
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            // Establish connection to the database
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//            // Query to check the user's credentials
//            String sql = "SELECT email, password FROM users WHERE email=?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                String dbEmail = rs.getString(1);
//                String dbPassword = rs.getString(2);
//
//                // Check if the provided credentials match
//                if (email.equals(dbEmail) && password.equals(dbPassword)) {
//                    
//                    // Create a session and set attributes
//                    HttpSession session = request.getSession();
//                    session.setAttribute("userEmail", email);
//                    session.setAttribute("isLoggedIn", true);
//                    session.setMaxInactiveInterval(30 * 60); // 30 minutes
//
//                    out.println("<script type='text/javascript'>");
//                    out.println("alert('Login successful!');");
//                    out.println("window.location.href='home.html';");
//                    out.println("</script>");
//                } else {
//                    out.println("<script type='text/javascript'>");
//                    out.println("alert('Invalid Email or Password. Please try again.');");
//                    out.println("window.location.href='login.html';");
//                    out.println("</script>");
//                }
//            } else {
//                out.println("<script type='text/javascript'>");
//                out.println("alert('Invalid Email or Password. Please try again.');");
//                out.println("window.location.href='login.html';");
//                out.println("</script>");
//            }
//
//        } catch (ClassNotFoundException e) {
//            out.println("<h3>Database driver not found.</h3>");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            out.println("<h3>Database error occurred.</h3>");
//            e.printStackTrace();
//        } finally {
//            // Close resources
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
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    private static final String DB_USER = "system";
//    private static final String DB_PASSWORD = "system";
//    
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        // Retrieve form data
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        
//        // Initialize database connection
//         Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs=null;
//
//        try {
//            // Load Oracle JDBC Driver
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            
//            // Establish connection to the database
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//            // Insert user data into the database
//            String sql = "select email,password from users where email=?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//
//            
//            rs=pstmt.executeQuery();
//            
//            if(rs.next()){
//                String em=rs.getString(1);
//                String ps=rs.getString(2);
//            if (email.equals(em) && password.equals(ps)) {
//                
//                // Set a cookie with user email
//                Cookie userCookie = new Cookie("userEmail", email);
//                userCookie.setMaxAge(24 * 60 * 60); // 1 day
//                response.addCookie(userCookie);
//
//                // Redirect to profile page
//                //response.sendRedirect("profile.html");
//
//
//                out.println("<script type=\'text/javascript\'>");
//                out.println("alert('Login successful!');");
//                out.println("window.location.href='home.html'");
//                out.println("</script>");
//                
//            
//            }else{
//                out.println("<script type=\'text/javascript\'>");
//                out.println("alert('Invalid Email or Password. Please try again.');");
//                out.println("window.location.href='login.html'");
//                out.println("</script>");
//            }
//            } else {
//                
//                out.println("<script type=\'text/javascript\'>");
//                out.println("alert('Invalid Email or Password. Please try again.');");
//                out.println("window.location.href='login.html'");
//                out.println("</script>");
//            }
//
//        } catch (ClassNotFoundException e) {
//            out.println("<h3>Database driver not found.</h3>");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            out.println("<h3>Database error occurred.</h3>");
//            e.printStackTrace();
//        } finally {
//            // Close resources
//            try {
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//                if(rs != null) rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}


/*
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Initialize database connection
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Query to verify email and password
            String sql = "select password from users where email = ?";
            //String sql="select * from users where email=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            
            
            if (rs.next()) {
                
                //out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
            
                // Compare the retrieved password with the user input
                String dbPassword = rs.getString("password");

                if (password.equals(dbPassword)) {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Login successful!');");
                    out.println("window.location.href='home.html';");
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Invalid Password. Please try again.');");
                    out.println("window.location.href='login.html';");
                    out.println("</script>");
                }
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid Email or Password. Please try again.');");
                out.println("window.location.href='login.html';");
                out.println("</script>");
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
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}*/

