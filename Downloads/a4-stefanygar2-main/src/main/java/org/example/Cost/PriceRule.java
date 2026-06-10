package org.example.Cost;


import org.example.Item;

import java.util.List;

public interface PriceRule {
    double priceToAggregate(List<Item> cart);
}
