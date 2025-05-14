package com.salon.model;

public class Booking {
    private int id;
    private int customerId;
    private int serviceId;
    private int employeeId;
    private String date;
    private String time;
    private String status;

    public Booking(int id, int customerId, int serviceId, int employeeId, String date, String time, String status) {
        this.id = id;
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.employeeId = employeeId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public int getServiceId() { return serviceId; }
    public int getEmployeeId() { return employeeId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}