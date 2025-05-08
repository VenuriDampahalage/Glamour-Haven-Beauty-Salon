package com.salon.controller;

import com.salon.service.EmployeeService;
import com.salon.service.FileHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee")
public class EmployeeController extends HttpServlet {
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setServletContext(getServletContext());

        employeeService = new EmployeeService();
        employeeService.setFileHandler(fileHandler);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("manage")) {
            request.setAttribute("employees", employeeService.getAllEmployees());
            request.getRequestDispatcher("employeeManagement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")) {
            String name = request.getParameter("name");
            String specialization = request.getParameter("specialization");
            employeeService.addEmployee(name, specialization);
            response.sendRedirect("employee?action=manage");
        } else if (action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String specialization = request.getParameter("specialization");
            employeeService.updateEmployee(id, name, specialization);
            response.sendRedirect("employee?action=manage");
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            employeeService.deleteEmployee(id);
            response.sendRedirect("employee?action=manage");
        }
    }
}