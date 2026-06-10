package org.example.Cost;


import org.example.Item;

import java.util.List;

public class ExtraCostForElectronics implements PriceRule {
    @Override
    public double priceToAggregate(List<Item> cart) {
        boolean hasAnElectronicDevice = cart.stream().anyMatch(it -> it.getType() == ItemType.ELECTRONIC);

        if(hasAnElectronicDevice)
            return 7.50;

        return 0;
    }
}
