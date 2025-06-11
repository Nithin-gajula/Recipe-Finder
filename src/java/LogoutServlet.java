import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.PrintWriter;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Set headers to prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        // Invalidate the session if one exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Invalidate the session
            session.invalidate();
//            out.println("<script type='text/javascript'>");
//            out.println("alert('You have successfully logged out.');");
//            out.println("window.location.href='login.html';");
//            out.println("</script>");
        }

        // Redirect to login page
        response.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//@WebServlet("/logout")
//public class LogoutServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        // Get the current session, if exists
//        HttpSession session = request.getSession(false);
//
//        if (session != null) {
//            // Invalidate the session
//            session.invalidate();
//            out.println("<script type='text/javascript'>");
//            out.println("alert('You have successfully logged out.');");
//            out.println("window.location.href='login.html';");
//            out.println("</script>");
//        } else {
//            // If no session exists, redirect to login
//            out.println("<script type='text/javascript'>");
//            out.println("alert('You are not logged in.');");
//            out.println("window.location.href='login.html';");
//            out.println("</script>");
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
