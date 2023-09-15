package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private final String currency;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final int id;

    public Order(String currency, List<OrderItem> items, OrderStatus status, int id) {
        this.currency = currency;
        this.items = items;
        this.status = status;
        this.id = id;
    }

    public static Order create(List<OrderItem> items, String currency, int id) {
        return new Order(
                currency,
                items,
                OrderStatus.CREATED,
                id
        );
    }

    public BigDecimal getTotal() {
        return items.stream().map(OrderItem::getTaxedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return items.stream().map(OrderItem::getTax).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
}
