package com.salon.service;

import com.salon.model.Review;
import java.util.ArrayList;
import java.util.List;
// import javax.servlet.ServletContext; // Old import
import jakarta.servlet.ServletContext; // Correct import

public class ReviewService {
    private List<Review> reviews = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();

    public ReviewService() {
        // reviews = fileHandler.loadReviews(); // Defer loading until context is set or handle differently
    }

    // Method to set FileHandler's ServletContext
    public void setFileHandlerContext(ServletContext context) {
        this.fileHandler.setServletContext(context);
        // Now that context is set, we can safely load reviews that depend on it.
        // However, the original loadReviews() loads service-specific reviews.
        // General salon reviews are loaded by HomeController.
        // If service-specific reviews also need context for their file path, this is where they'd be loaded.
        // For now, we primarily need the context for saving new salon reviews.
    }

    public void addReview(int customerId, int serviceId, String comment) {
        int id = reviews.size() + 1;
        reviews.add(new Review(id, customerId, serviceId, comment));
        fileHandler.saveReviews(reviews);
    }

    // New method for general salon reviews
    public void addSalonReview(String username, String reviewText) {
        // We need a way to save this to reviews.txt
        // This will likely involve a new method in FileHandler
        fileHandler.saveSalonReview(username, reviewText);
    }

    public List<Review> getAllReviews() {
        return reviews;
    }
}