package de.lachcrafter.lachMoney.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final int DECIMALS = 2;

    private final BigDecimal amount;

    public Money(String amount) {
        this.amount = new BigDecimal(amount).setScale(DECIMALS, ROUNDING_MODE);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}