package main;

import model.Amount;

/**
 *
 * @author henar
 */
public interface Payable {
    boolean pay(Amount amount);
}