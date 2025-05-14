package com.salon.service;

import com.salon.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    //store Employee objects
    private List<Employee> employees = new ArrayList<>();
    //handling Employee data
    private FileHandler fileHandler;

    public EmployeeService() {

        fileHandler = new FileHandler();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        employees = fileHandler.loadEmployees();
    }

    public void addEmployee(String name, String specialization) {
        int id = employees.size() + 1;
        employees.add(new Employee(id, name, specialization));
        fileHandler.saveEmployees(employees);
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
        fileHandler.saveEmployees(employees);
    }

    public void updateEmployee(int id, String name, String specialization) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.set(employees.indexOf(employee), new Employee(id, name, specialization));
                fileHandler.saveEmployees(employees);
                break;
            }
        }
    }
}