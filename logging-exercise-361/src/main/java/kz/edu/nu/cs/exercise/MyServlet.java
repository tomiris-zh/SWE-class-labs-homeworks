package kz.edu.nu.cs.exercise;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(urlPatterns = { "/myservlet" })
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ArrayList<String> requestInfos = new ArrayList<String>();

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n\n");

        requestInfos.add("Date: " + String.valueOf(new java.util.Date()) + "; Host: " + request.getRemoteHost() + "; Path: "
                         + request.getContextPath());

        response.getWriter().append(String.valueOf(requestInfos));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
