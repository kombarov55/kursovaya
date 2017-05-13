package web;

import db.ClientDAO;
import db.UserRoleDAO;
import dto.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationHandler implements HttpRequestHandler {

    @Autowired ClientDAO clientDAO;
    @Autowired UserRoleDAO userRoleDAO;

    @Override public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        if (isValid(username, password, passwordRepeat)) {
            Client client = new Client(username, password, userRoleDAO.getByName("user"));
            clientDAO.save(client);
            request.getSession().setAttribute("username", username);
            doSuccessRedirect(response);
        } else {
            doFailRedirect(response);
        }
    }

    private boolean isValid(String username, String password, String passwordRepeat) throws ServletException, IOException {
        return username.contains("@") && usernameUnique(username) && password.equals(passwordRepeat);
    }

    private void doSuccessRedirect(HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/app/");
    }

    private void doFailRedirect(HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/register.html?fail=true");
    }

    private boolean usernameUnique(String username) {
        return clientDAO.findByUsername(username) == null;
    }

}