package cdzero2hero.web;

import cdzero2hero.domain.User;
import cdzero2hero.repository.DbUserRepository;
import cdzero2hero.repository.UserRepository;
import cdzero2hero.util.Config;
import cdzero2hero.util.EnvironmentDetection;
import cdzero2hero.util.ServerInfo;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    public static boolean shuttingDown;

    private UserRepository userRepository = DbUserRepository.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session != null) && (session.getAttribute("user") != null)) {
            response.sendRedirect("/friends");
            return;
        }

        ServerInfo serverInfo = new ServerInfo();
        Map<String, Object> values = new HashMap<>();
        values.put("server", serverInfo.getHostName());
        values.put("version", serverInfo.getVersion());
        values.put("env", EnvironmentDetection.detectEnvironment());
        values.put("bgcolor", Config.INSTANCE.getBackgroundColor());

        response.setCharacterEncoding("UTF-8");
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        Mustache mustache = mustacheFactory.compile("templates/login.html");
        mustache.execute(response.getWriter(), values).flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("user");
        System.out.println("User: " + name);

        if (name != null && !name.isEmpty()) {
            User user = userRepository.getUserByName(name.trim());
            if (user == null) {
                user = new User(name.trim());
                userRepository.addUser(user);
            }

            request.getSession(true).setAttribute("user", user.getId().toString());
            response.sendRedirect("/friends");
            return;
        }

        response.sendRedirect("/");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);

        if (shuttingDown) {
            resp.setStatus(404);
        }
    }
}