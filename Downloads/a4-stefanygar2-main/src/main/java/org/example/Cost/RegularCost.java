package org.example.Cost;


import org.example.Item;

import java.util.List;

public class RegularCost implements PriceRule {
    @Override
    public double priceToAggregate(List<Item> cart) {

        double price = 0;
        for (Item item : cart) {
            price += item.getPricePerUnit() * item.getQuantity();
        }

        return price;
    }
}
