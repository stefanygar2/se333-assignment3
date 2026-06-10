package org.example;

import org.example.Cost.ItemType;
import org.example.Cost.PriceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {

    private ShoppingCart cart;

    static class TestShoppingCart implements ShoppingCart {
        private final List<Item> items = new ArrayList<>();

        @Override
        public void add(Item item) {
            items.add(item);
        }

        @Override
        public List<Item> getItems() {
            return items;
        }

        @Override
        public int numberOfItems() {
            return items.size();
        }
    }

    @BeforeEach
    void setup() {
        cart = new TestShoppingCart();
    }

    @Test
    @DisplayName("specification-based")
    void addToCartStoresItem() {
        Amazon amazon = new Amazon(cart, List.of());

        amazon.addToCart(
                new Item(ItemType.ELECTRONIC, "IPAD", 2, 250.00)
        );

        assertEquals(1, cart.getItems().size());
    }

    @Test
    @DisplayName("structural-based")
    void calculateAggregatesRules() {

        cart.add(
                new Item(ItemType.OTHER, "BOOK2", 2, 20.00)
        );

        PriceRule rule = items -> items.size();

        Amazon amazon = new Amazon(cart, List.of(rule));

        assertEquals(1.0, amazon.calculate());
    }
}