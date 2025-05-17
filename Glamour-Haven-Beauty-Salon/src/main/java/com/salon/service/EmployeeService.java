package com.salon.service;

import com.salon.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee> employees = new ArrayList<>();
    private FileHandler fileHandler;

    public EmployeeService() {
        fileHandler = new FileHandler();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        employees = fileHandler.loadEmployees();
    }

    public void addEmployee(String name, String specialization, String imagePath) {
        int id = employees.size() + 1;
        Employee employee = new Employee(id, name, specialization, imagePath);
        employees.add(employee);
        fileHandler.saveEmployees(employees);
    }

    public List<Employee> getAllEmployees() {
        if (this.fileHandler != null) {
            this.employees = this.fileHandler.loadEmployees();
        }
        return employees;
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
        fileHandler.saveEmployees(employees);
    }

    public void updateEmployee(int id, String name, String specialization, String imagePath) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                String finalImagePath = imagePath != null ? imagePath : employee.getImagePath();
                employees.set(employees.indexOf(employee), new Employee(id, name, specialization, finalImagePath));
                fileHandler.saveEmployees(employees);
                break;
            }
        }
    }

    public Employee getEmployeeById(int id) {
        if (this.fileHandler != null && (this.employees == null || this.employees.isEmpty())) {
            this.employees = this.fileHandler.loadEmployees();
        }
        if (this.employees != null) {
            for (Employee employee : this.employees) {
                if (employee.getId() == id) {
                    return employee;
                }
            }
        }
        return null;
    }
}