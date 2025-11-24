package mk.ukim.finki.wp.iblab1.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.iblab1.model.User;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "VerifyServlet", urlPatterns = "/verify")
public class VerifyServlet extends HttpServlet {
    private final SpringTemplateEngine templateEngine;

    public VerifyServlet(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        templateEngine.process("verify.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String codeInput = req.getParameter("code");

        Integer codeSaved = (Integer) req.getSession().getAttribute("2faCode");
        User pendingUser = (User) req.getSession().getAttribute("pendingUser");

        if (pendingUser != null && codeSaved != null && codeSaved.toString().equals(codeInput)) {
            req.getSession().setAttribute("user", pendingUser);
            req.getSession().removeAttribute("pendingUser");
            req.getSession().removeAttribute("2faCode");
            resp.sendRedirect("/welcome");
        } else {
            resp.sendRedirect("/verify?error");
        }
    }
}
