package mk.ukim.finki.wp.iblab1.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.iblab1.bootstrap.DataHolder;
import mk.ukim.finki.wp.iblab1.model.User;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final SpringTemplateEngine templateEngine;

    public LoginServlet(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        templateEngine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        for (User u : DataHolder.users) {
            if (u.getUsername().equals(username)) {
                String hashed = RegisterServlet.hashPassword(password);
                if (u.getPassword().equals(hashed)) {

                    // 1. Генерирај 2FA код
                    int code = (int) (100000 + Math.random() * 900000);

                    // 2. Стави го user како pending
                    req.getSession().setAttribute("pendingUser", u);

                    // 3. Зачувај го кодот
                    req.getSession().setAttribute("2faCode", code);

                    System.out.println("2FA CODE: " + code); // само за тест

                    // 4. Префрли на /verify
                    resp.sendRedirect("/verify");
                    return;
                }
            }
        }

        resp.getWriter().println("Invalid login attempt!");
    }
}

