/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

/**
 *
 * @author henar
 */
public class Employee extends Person implements Logable{
    private int employeeId;
    private String password;
    
    private static final int EMPLOYEE_ID = 123;
    private static final String PASSWORD = "test";
    
    public Employee(String name) {
        super(name);
        this.employeeId = EMPLOYEE_ID;
        this.password = PASSWORD;
    }
    
    public Employee() {
        this("");
    }
    
    @Override
    public boolean login(int user, String password) {
        return this.employeeId == user && this.password.equals(password);
    }
}
