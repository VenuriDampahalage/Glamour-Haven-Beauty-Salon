package com.salon.service;

import com.salon.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    private FileHandler fileHandler;

    public CustomerService() {
        // Initialize with empty list, will be populated when FileHandler is set
        customers = new ArrayList<>();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        if (fileHandler != null) {
            customers = fileHandler.loadCustomers();
        }
    }

    public boolean register(String username, String password, String email) {
        int id = customers.size() + 1;
        String role = "admin".equals(username) ? "admin" : "user";
        Customer customer = new Customer(id, username, password, email, role);
        customers.add(customer);
        if (fileHandler != null) {
            fileHandler.saveCustomers(customers);
        }
        return true;
    }

    public Customer login(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        // Always load fresh customers from the file handler
        if (fileHandler != null) {
            this.customers = fileHandler.loadCustomers();
        }
        return customers;
    }

    public void deleteCustomer(int id) {
        customers.removeIf(customer -> customer.getId() == id);
        if (fileHandler != null) {
            fileHandler.saveCustomers(customers);
        }
    }

    public void updateCustomer(int id, String username, String password, String email) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                customers.set(customers.indexOf(customer), new Customer(id, username, password, email));
                if (fileHandler != null) {
                    fileHandler.saveCustomers(customers);
                }
                break;
            }
        }
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == updatedCustomer.getId()) {
                customers.set(i, updatedCustomer);
                if (fileHandler != null) {
                    fileHandler.saveCustomers(customers);
                }
                return true;
            }
        }
        return false;
    }
}