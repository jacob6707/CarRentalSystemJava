package dev.jacob6707.carrentalsystem.entities;

import java.math.BigDecimal;

public interface Rentable {
    boolean isAvailable();
    void rent();
    void returnBack();
    BigDecimal getDailyPrice();
}
