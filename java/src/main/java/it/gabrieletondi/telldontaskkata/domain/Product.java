package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Product {
    private final String name;
    private final BigDecimal price;
    private final Category category;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public BigDecimal taxedAmount() {
        return price.add(tax()).setScale(2, HALF_UP);
    }

    public BigDecimal tax() {
        return price.divide(valueOf(100)).multiply(category.getTaxPercentage()).setScale(2, HALF_UP);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
