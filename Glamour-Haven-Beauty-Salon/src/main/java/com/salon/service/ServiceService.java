package com.salon.service;

import com.salon.model.Service;
import java.util.ArrayList;
import java.util.List;

public class ServiceService {
    private List<Service> services = new ArrayList<>();
    private FileHandler fileHandler;

    public ServiceService() {
        // Initialize with empty list, will be populated when FileHandler is set
        services = new ArrayList<>();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        if (fileHandler != null) {
            services = fileHandler.loadServices();
        }
    }

    public List<Service> getAllServices() {
        return services;
    }

    public Service getServiceById(int id) {
        return services.stream()
                .filter(service -> service.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addService(Service service) {
        services.add(service);
        if (fileHandler != null) {
            fileHandler.saveServices(services);
        }
    }

    public void updateService(int id, String name, double price, String description, String imagePath) {
        for (Service service : services) {
            if (service.getId() == id) {
                service.setName(name);
                service.setPrice(price);
                service.setDescription(description);
                if (imagePath != null) {
                    service.setImagePath(imagePath);
                }
                if (fileHandler != null) {
                    fileHandler.saveServices(services);
                }
                break;
            }
        }
    }

    public void deleteService(int id) {
        services.removeIf(service -> service.getId() == id);
        if (fileHandler != null) {
            fileHandler.saveServices(services);
        }
    }
}