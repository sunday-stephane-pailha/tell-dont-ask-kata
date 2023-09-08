package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.OrderCreationUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final BigDecimal total;
    private final String currency;
    private final List<OrderItem> items;
    private final BigDecimal tax;
    private OrderStatus status;
    private final int id;

    public Order(BigDecimal total, String currency, List<OrderItem> items, BigDecimal tax, OrderStatus status, int id) {
        this.total = total;
        this.currency = currency;
        this.items = items;
        this.tax = tax;
        this.status = status;
        this.id = id;
    }

    public static Order create(ArrayList<OrderCreationUseCase.Truc> trucs, String currency, int id) {
        List<OrderItem> items = trucs.stream().map(OrderCreationUseCase.Truc::items).toList();
        BigDecimal total = trucs.stream().map(OrderCreationUseCase.Truc::taxedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal tax = trucs.stream().map(OrderCreationUseCase.Truc::taxAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Order(
                total,
                currency,
                items,
                tax,
                OrderStatus.CREATED,
                id
        );
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
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
