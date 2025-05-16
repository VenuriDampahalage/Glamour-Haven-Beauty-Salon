package com.salon.controller;

import com.salon.model.Customer;
import com.salon.service.ReviewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ReviewController")
public class ReviewController extends HttpServlet {
    private ReviewService reviewService;

    @Override
    public void init() {
        reviewService = new ReviewService();
        reviewService.setFileHandlerContext(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("customer") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Customer customer = (Customer) session.getAttribute("customer");
            String reviewText = request.getParameter("reviewText");

            if (reviewText != null && !reviewText.trim().isEmpty()) {
                reviewService.addSalonReview(customer.getUsername(), reviewText);
                request.getSession().setAttribute("reviewMessage", "Review submitted successfully!");
            } else {
                request.getSession().setAttribute("reviewError", "Review text cannot be empty.");
            }
            response.sendRedirect(request.getContextPath() + "/CustomerController?action=viewDashboard");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("showReviewForm".equals(action)) {
            // Ensure user is logged in before showing the form
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("customer") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            request.getRequestDispatcher("/reviewForm.jsp").forward(request, response);
        } else {
            // Default GET behavior (or handle other actions)
            response.sendRedirect(request.getContextPath() + "/CustomerController?action=viewDashboard");
        }
    }
}