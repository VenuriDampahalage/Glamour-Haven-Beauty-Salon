package com.salon.model;

public class Employee {
    private int id;
    private String name;
    private String specialization;

    //Initializes a new instance of Employee with default values.
    public Employee() {
    }

    //Initializes a new instance of Employee with the given values.
    public Employee(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    // return the employee's ID
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // gets the specialization of the employee
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}