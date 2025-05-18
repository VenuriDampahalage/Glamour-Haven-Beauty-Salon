package com.salon.service;

import com.salon.model.Customer;
import com.salon.model.Booking;
import com.salon.model.Service;
import com.salon.model.Review;
import com.salon.model.Employee;
import jakarta.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class FileHandler {
    private static final String USER_FILE = "/WEB-INF/classes/users.txt";
    private static final String BOOKING_FILE = "/WEB-INF/classes/bookings.txt";
    private static final String SERVICE_FILE = "/WEB-INF/classes/services.txt";
    private static final String REVIEW_FILE = "/WEB-INF/classes/reviews.txt";
    private static final String EMPLOYEE_FILE = "/WEB-INF/classes/employees.txt";
    private static final String SALON_GENERAL_REVIEW_FILE = "/WEB-INF/salon_general_reviews.txt";
    private ServletContext context;

    public FileHandler() {
        // Initialize files will be called when context is set
    }

    public void setServletContext(ServletContext context) {
        this.context = context;
        initializeFiles();
    }

    private void initializeFiles() {
        try {
            createFileIfNotExists(USER_FILE);
            createFileIfNotExists(BOOKING_FILE);
            createFileIfNotExists(SERVICE_FILE);
            createFileIfNotExists(REVIEW_FILE);
            createFileIfNotExists(EMPLOYEE_FILE);
            createFileIfNotExists(SALON_GENERAL_REVIEW_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        String realPath = context.getRealPath(filePath);
        File file = new File(realPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            String realPath = context.getRealPath(USER_FILE);
            try (BufferedReader reader = new BufferedReader(new FileReader(realPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        // New format with role
                        customers.add(new Customer(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4]
                        ));
                    } else {
                        // Old format without role
                        customers.add(new Customer(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                parts[3]
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void saveCustomers(List<Customer> customers) {
        try {
            String realPath = context.getRealPath(USER_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath))) {
                for (Customer customer : customers) {
                    writer.write(String.format("%d,%s,%s,%s,%s",
                            customer.getId(),
                            customer.getUsername(),
                            customer.getPassword(),
                            customer.getEmail(),
                            customer.getRole()
                    ));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try {
            String realPath = context.getRealPath(BOOKING_FILE);
            try (BufferedReader reader = new BufferedReader(new FileReader(realPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    bookings.add(new Booking(
                            Integer.parseInt(parts[0]), // id
                            Integer.parseInt(parts[1]), // customerId
                            Integer.parseInt(parts[2]), // serviceId
                            Integer.parseInt(parts[3]), // employeeId
                            parts[4],                   // date
                            parts[5],                   // time
                            parts.length > 6 ? parts[6] : "Pending" // status (default to "Pending" if not present)
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void saveBookings(List<Booking> bookings) {
        try {
            String realPath = context.getRealPath(BOOKING_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath))) {
                for (Booking booking : bookings) {
                    writer.write(booking.getId() + "," +
                            booking.getCustomerId() + "," +
                            booking.getServiceId() + "," +
                            booking.getEmployeeId() + "," +
                            booking.getDate() + "," +
                            booking.getTime() + "," +
                            booking.getStatus());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Service> loadServices() {
        List<Service> services = new ArrayList<>();
        try {
            String realPath = context.getRealPath(SERVICE_FILE);
            try (BufferedReader reader = new BufferedReader(new FileReader(realPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    // Handle both old and new formats
                    if (parts.length == 3) {
                        // Old format: id, name, price
                        services.add(new Service(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                Double.parseDouble(parts[2]),
                                "", // Empty description
                                "default-service-image.jpg" // Default image
                        ));
                    } else if (parts.length == 5) {
                        // New format: id, name, price, description, imagePath
                        services.add(new Service(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                Double.parseDouble(parts[2]),
                                parts[3],
                                parts[4]
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    public void saveServices(List<Service> services) {
        try {
            String realPath = context.getRealPath(SERVICE_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath))) {
                for (Service service : services) {
                    writer.write(service.getId() + "," +
                            service.getName() + "," +
                            service.getPrice() + "," +
                            service.getDescription() + "," +
                            service.getImagePath());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Review> loadReviews() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REVIEW_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                reviews.add(new Review(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void saveReviews(List<Review> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEW_FILE))) {
            for (Review review : reviews) {
                writer.write(review.getId() + "," + review.getCustomerId() + "," + review.getServiceId() + "," + review.getComment());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            // First try WEB-INF/classes location
            String realPath = context.getRealPath(EMPLOYEE_FILE);
            System.out.println("Trying to load employees from WEB-INF: " + realPath);

            File webInfFile = new File(realPath);
            File resourceFile = new File("src/main/resources/employees.txt");

            // If file doesn't exist in WEB-INF, try resources
            if (!webInfFile.exists() && resourceFile.exists()) {
                System.out.println("Loading from resources instead: " + resourceFile.getAbsolutePath());
                realPath = resourceFile.getAbsolutePath();
            }

            // If neither file exists, create with default data
            if (!webInfFile.exists() && !resourceFile.exists()) {
                System.out.println("No employee file found. Creating with default data.");
                List<Employee> defaultEmployees = Arrays.asList(
                        new Employee(1, "John Smith", "Hair Stylist", null),
                        new Employee(2, "Emma Johnson", "Nail Technician", null),
                        new Employee(3, "Michael Brown", "Massage Therapist", null)
                );
                saveEmployees(defaultEmployees);
                return defaultEmployees;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(realPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String imagePath = parts.length > 3 ? parts[3] : null;
                    Employee employee = new Employee(Integer.parseInt(parts[0]), parts[1], parts[2], imagePath);
                    System.out.println("Loaded employee: " + employee.getName());
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading employees: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Total employees loaded: " + employees.size());
        return employees;
    }

    public void saveEmployees(List<Employee> employees) {
        try {
            String realPath = context.getRealPath(EMPLOYEE_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath))) {
                for (Employee employee : employees) {
                    writer.write(employee.getId() + "," +
                            employee.getName() + "," +
                            employee.getSpecialization() + "," +
                            (employee.getImagePath() != null ? employee.getImagePath() : ""));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSalonReview(String username, String reviewText) {
        if (context == null) {
            System.err.println("FileHandler ERROR: ServletContext is not set. Cannot save salon review.");
            return;
        }
        String salonReviewFilePath = context.getRealPath(SALON_GENERAL_REVIEW_FILE);
        if (salonReviewFilePath == null) {
            System.err.println("FileHandler ERROR: Could not resolve real path for " + SALON_GENERAL_REVIEW_FILE + ". Review not saved.");
            return;
        }
        System.out.println("FileHandler INFO: Attempting to save salon review to: " + salonReviewFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(salonReviewFilePath, true))) { // true for append mode
            writer.write(username + ":::" + reviewText.replace("\n", "<br>"));
            writer.newLine();
            System.out.println("FileHandler INFO: Salon review saved successfully for user: " + username);
        } catch (IOException e) {
            System.err.println("FileHandler ERROR: IOException while saving salon review to " + salonReviewFilePath);
            e.printStackTrace();
        }
    }

    public List<String[]> loadSalonReviews() {
        List<String[]> salonReviews = new ArrayList<>();
        if (context == null) {
            System.err.println("FileHandler ERROR: ServletContext is not set. Cannot load salon reviews.");
            return salonReviews;
        }
        String salonReviewFilePath = context.getRealPath(SALON_GENERAL_REVIEW_FILE);
        if (salonReviewFilePath == null) {
            System.err.println("FileHandler ERROR: Could not resolve real path for " + SALON_GENERAL_REVIEW_FILE + ". Cannot load reviews.");
            return salonReviews;
        }
        System.out.println("FileHandler INFO: Attempting to load salon reviews from: " + salonReviewFilePath);
        File file = new File(salonReviewFilePath);
        if (!file.exists()) {
            System.out.println("FileHandler INFO: Salon reviews file does not exist at: " + salonReviewFilePath + ". Returning empty list.");
            return salonReviews;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":::", 2);
                if (parts.length == 2) {
                    salonReviews.add(parts);
                    count++;
                }
            }
            System.out.println("FileHandler INFO: Loaded " + count + " salon reviews from " + salonReviewFilePath);
        } catch (IOException e) {
            System.err.println("FileHandler ERROR: IOException while loading salon reviews from " + salonReviewFilePath);
            e.printStackTrace();
        }
        return salonReviews;
    }
}