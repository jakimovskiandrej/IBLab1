package mk.ukim.finki.wp.iblab1.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.iblab1.model.User;

import java.io.IOException;

@WebServlet(name = "WelcomeServlet", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect("register.html");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<!DOCTYPE html>");
        resp.getWriter().println("<html><head><title>Добредојде</title></head><body>");
        resp.getWriter().println("<h2>Добредојде, " + user.getUsername() + "!</h2>");
        resp.getWriter().println("<p>Успешно се најавивте во системот.</p>");
        resp.getWriter().println("<form action='logout' method='get'>");
        resp.getWriter().println("<input type='submit' value='Одјава'>");
        resp.getWriter().println("</form>");
        resp.getWriter().println("</body></html>");
    }
}
