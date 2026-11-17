package com.training.employeeservice.dto;

import jakarta.validation.constraints.NotBlank;

public class EmployeeRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Department must not be blank")
    private String department;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
