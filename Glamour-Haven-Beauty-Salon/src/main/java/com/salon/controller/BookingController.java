package com.salon.controller;

import com.salon.model.Booking;
import com.salon.model.Service;
import com.salon.model.Employee;
import com.salon.service.BookingService;
import com.salon.service.ServiceService;
import com.salon.service.EmployeeService;
import com.salon.service.FileHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/BookingController")
public class BookingController extends HttpServlet {
    private BookingService bookingService;
    private ServiceService serviceService;
    private EmployeeService employeeService;
    private FileHandler fileHandler;

    @Override
    public void init() throws ServletException {
        try {
            fileHandler = new FileHandler();
            fileHandler.setServletContext(getServletContext());
            bookingService = new BookingService();
            bookingService.setFileHandler(fileHandler);
            serviceService = new ServiceService();
            serviceService.setFileHandler(fileHandler);
            employeeService = new EmployeeService();
            employeeService.setFileHandler(fileHandler);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize BookingController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("viewBookings".equals(action)) {
            request.setAttribute("bookings", bookingService.getAllBookings());
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
        } else if ("manage".equals(action)) {
            // For admin booking management
            request.setAttribute("bookings", bookingService.getAllBookings());
            request.getRequestDispatcher("bookingManagement.jsp").forward(request, response);
        } else {
            // Default to showing all bookings instead of redirecting to services
            request.setAttribute("bookings", bookingService.getAllBookings());
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("book".equals(action)) {
            HttpSession session = request.getSession();
            Integer customerId = null;
            Object customerObj = session.getAttribute("customer");
            if (customerObj instanceof com.salon.model.Customer) {
                 customerId = ((com.salon.model.Customer) customerObj).getId();
            } else if (customerObj instanceof Integer) {
                customerId = (Integer) customerObj;
            }
            
            if (customerId == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            
            int bookingId = bookingService.getAllBookings().size() + 1;
            Booking booking = new Booking(bookingId, customerId, serviceId, employeeId, date, time, "Pending");
            bookingService.addBooking(booking);
            
            Service service = serviceService.getServiceById(serviceId);
            Employee employee = employeeService.getEmployeeById(employeeId);
            
            request.setAttribute("booking", booking);
            request.setAttribute("service", service);
            request.setAttribute("bookedEmployee", employee);
            
            request.getRequestDispatcher("/bookingConfirmation.jsp").forward(request, response);
        } else if ("updateStatus".equals(action)) {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String status = request.getParameter("status");
            
            try {
                bookingService.updateBookingStatus(bookingId, status);
                response.setContentType("text/plain");
                response.getWriter().write("success");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("error");
            }
        }
    }
}