package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(SellItemsRequest request) {
        var trucs = new ArrayList<Truc>();

        for (SellItemRequest itemRequest : request.getRequests()) {
            Product product = productCatalog.getByName(itemRequest.getProductName());

            if (product == null) {
                throw new UnknownProductException();
            }
            else {
                final OrderItem orderItem = new OrderItem(
                        product,
                        itemRequest.getQuantity()
                );
                trucs.add(new Truc(orderItem, orderItem.getTaxedAmount(), orderItem.getTax()));
            }
        }

        var order = Order.create(trucs, "EUR", 0);

        orderRepository.save(order);
    }

    public record Truc(OrderItem items, BigDecimal taxedAmount, BigDecimal taxAmount) {

    }
}
