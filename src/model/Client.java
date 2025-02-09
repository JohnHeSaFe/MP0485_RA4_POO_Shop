/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

/**
 *
 * @author henar
 */
public class Client extends Person implements Payable{
    private int memberId;
    private Amount balance;
    
    private static final int MEMBER_ID = 456;
    private static final Amount BALANCE = new Amount(50);
    
    public Client(String name) {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = BALANCE;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean pay(Amount amount) {
        balance.setValue(balance.getValue() - amount.getValue());
        
        return balance.getValue() >= 0;
    }
}