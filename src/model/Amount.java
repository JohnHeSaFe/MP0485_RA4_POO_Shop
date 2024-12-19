/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author henar
 */
public class Amount {
    private double value;
    private final String currency = "?";
    
    public Amount(double value) {
        this.value = value;
    }
    
    public Amount() {
        this(0.0);
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public void addValue(double value) {
        this.value += value;
    }
    
    @Override
    public String toString() {
        return this.value + this.currency;
    }
}
