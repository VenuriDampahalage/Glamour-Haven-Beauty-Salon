package com.salon.controller;

import com.salon.model.Service;
import com.salon.model.Employee;
import com.salon.service.ServiceService;
import com.salon.service.FileHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/ServiceController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,    // 2MB
    maxFileSize = 1024 * 1024 * 50,         // 50MB
    maxRequestSize = 1024 * 1024 * 100      // 100MB
)
public class ServiceController extends HttpServlet {
    private ServiceService serviceService;
    private FileHandler fileHandler;
    private static final String UPLOAD_DIR = "service_images";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 50; // 50MB

    @Override
    public void init() throws ServletException {
        try {
            fileHandler = new FileHandler();
            fileHandler.setServletContext(getServletContext());
            serviceService = new ServiceService();
            serviceService.setFileHandler(fileHandler);
            
            // Create upload directory in the webapp folder
            String webappPath = getServletContext().getRealPath("/");
            File uploadDir = new File(webappPath, UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
                System.out.println("Created upload directory at: " + uploadDir.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error in init(): " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Failed to initialize ServiceController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("list")) {
            List<Service> services = serviceService.getAllServices();
            System.out.println("Loading services, found: " + services.size());
            request.setAttribute("services", services);
            
            // Load salon reviews
            List<String[]> salonReviews = fileHandler.loadSalonReviews();
            if (salonReviews == null || salonReviews.isEmpty()) {
                salonReviews = new ArrayList<>();
                salonReviews.add(new String[]{"Alice Wonderland", "Absolutely loved the ambiance and the stylists were so professional. My hair has never looked better!"});
                salonReviews.add(new String[]{"Bob The Builder", "A truly relaxing experience from start to finish. The massage service was top-notch. Highly recommend!"});
                salonReviews.add(new String[]{"Charlie Brown", "Great service and friendly staff. They really listen to what you want. I'll definitely be back for my next haircut."});
            }
            request.setAttribute("salonReviews", salonReviews);
            
            request.getRequestDispatcher("services.jsp").forward(request, response);
        } else if (action.equals("manage")) {
            request.setAttribute("services", serviceService.getAllServices());
            request.getRequestDispatcher("serviceManagement.jsp").forward(request, response);
        } else if (action.equals("view")) {
            int serviceId = Integer.parseInt(request.getParameter("id"));
            Service service = serviceService.getServiceById(serviceId);
            
            // Load employees for the booking form
            List<Employee> employees = fileHandler.loadEmployees();
            
            request.setAttribute("service", service);
            request.setAttribute("employees", employees);
            request.getRequestDispatcher("serviceDetails.jsp").forward(request, response);
        } else if (action.equals("table")) {
            request.setAttribute("services", serviceService.getAllServices());
            request.getRequestDispatcher("serviceTable.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Processing action: " + action);
        
        if ("addService".equals(action)) {
            try {
                String name = request.getParameter("name");
                double price = Double.parseDouble(request.getParameter("price"));
                String description = request.getParameter("description");
                
                System.out.println("Adding new service: " + name);
                
                // Handle file upload
                Part filePart = request.getPart("image");
                
                // Validate file size
                if (filePart.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("error", "File size exceeds maximum limit of 50MB");
                    request.getRequestDispatcher("serviceForm.jsp").forward(request, response);
                    return;
                }
                
                // Validate file type
                String contentType = filePart.getContentType();
                if (!contentType.startsWith("image/")) {
                    request.setAttribute("error", "Only image files are allowed");
                    request.getRequestDispatcher("serviceForm.jsp").forward(request, response);
                    return;
                }
                
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                
                // Save to permanent location in webapp directory
                String webappPath = getServletContext().getRealPath("/");
                File uploadDir = new File(webappPath, UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                File targetFile = new File(uploadDir, uniqueFileName);
                System.out.println("Saving image to: " + targetFile.getAbsolutePath());
                
                // Copy the file
                filePart.write(targetFile.getAbsolutePath());
                String imagePath = UPLOAD_DIR + "/" + uniqueFileName;
                
                int id = serviceService.getAllServices().size() + 1;
                Service service = new Service(id, name, price, description, imagePath);
                serviceService.addService(service);
                
                System.out.println("Service added successfully with ID: " + id);
                response.sendRedirect("ServiceController?action=manage");
            } catch (Exception e) {
                System.err.println("Error adding service: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("error", "Error adding service: " + e.getMessage());
                request.getRequestDispatcher("serviceForm.jsp").forward(request, response);
            }
        } else if ("updateService".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                double price = Double.parseDouble(request.getParameter("price"));
                String description = request.getParameter("description");
                
                System.out.println("Updating service with ID: " + id);
                
                // Handle file upload if new image is provided
                Part filePart = request.getPart("image");
                String imagePath = null;
                
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    
                    // Save to permanent location in webapp directory
                    String webappPath = getServletContext().getRealPath("/");
                    File uploadDir = new File(webappPath, UPLOAD_DIR);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    
                    File targetFile = new File(uploadDir, uniqueFileName);
                    System.out.println("Saving updated image to: " + targetFile.getAbsolutePath());
                    
                    filePart.write(targetFile.getAbsolutePath());
                    imagePath = UPLOAD_DIR + "/" + uniqueFileName;
                }
                
                serviceService.updateService(id, name, price, description, imagePath);
                System.out.println("Service updated successfully");
                response.sendRedirect("ServiceController?action=manage");
            } catch (Exception e) {
                System.err.println("Error updating service: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("ServiceController?action=manage");
            }
        } else if ("deleteService".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println("Deleting service with ID: " + id);
                serviceService.deleteService(id);
                System.out.println("Service deleted successfully");
                response.sendRedirect("ServiceController?action=manage");
            } catch (Exception e) {
                System.err.println("Error deleting service: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("ServiceController?action=manage");
            }
        }
    }
}