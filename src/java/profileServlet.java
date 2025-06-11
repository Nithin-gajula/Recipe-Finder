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

