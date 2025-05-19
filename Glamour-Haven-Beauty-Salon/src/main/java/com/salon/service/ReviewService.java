package com.salon.service;

import com.salon.model.Review;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext; // Correct import

public class ReviewService {
    private List<Review> reviews = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();

    public ReviewService() {
    }


    public void setFileHandlerContext(ServletContext context) {
        this.fileHandler.setServletContext(context);
    
    }

    public void addReview(int customerId, int serviceId, String comment) {
        int id = reviews.size() + 1;
        reviews.add(new Review(id, customerId, serviceId, comment));
        fileHandler.saveReviews(reviews);
    }


    public void addSalonReview(String username, String reviewText) {
    
        fileHandler.saveSalonReview(username, reviewText);
    }

    public List<Review> getAllReviews() {
        return reviews;
    }
}
