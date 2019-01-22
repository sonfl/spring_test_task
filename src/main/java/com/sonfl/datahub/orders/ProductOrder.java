package com.sonfl.datahub.orders;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductOrder {

    private OrderingHistory history;

    @Autowired
    public ProductOrder(OrderingHistory history) {
        this.history = history;
    }

    public void processProducts(Long[] data) {
        for (long l :
                data) {
            process(l);
        }
    }

    private void process(long l) {
        /*
        Logic to order products
        */

        history.add(l);
    }

}
