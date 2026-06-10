
package org.example;

import org.example.Cost.PriceRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmazonUnitTest {

        @Mock
        ShoppingCart cart;

        @Mock
        PriceRule rule1;

        @Mock
        PriceRule rule2;

        @InjectMocks
        Amazon amazon;

        @Test
        @DisplayName("specification-based: calculate sums all price rules correctly")
        void calculate_specification_based() {
                when(cart.getItems()).thenReturn(List.of());

                when(rule1.priceToAggregate(any())).thenReturn(10.0);
                when(rule2.priceToAggregate(any())).thenReturn(5.0);

                Amazon amazon = new Amazon(cart, List.of(rule1, rule2));

                double result = amazon.calculate();

                assertEquals(15.0, result);
        }

        @Test
        @DisplayName("structural-based: verify each rule is invoked exactly once")
        void calculate_structural_based() {
                when(cart.getItems()).thenReturn(List.of());

                when(rule1.priceToAggregate(any())).thenReturn(2.0);
                when(rule2.priceToAggregate(any())).thenReturn(3.0);

                Amazon amazon = new Amazon(cart, List.of(rule1, rule2));

                amazon.calculate();

                verify(rule1, times(1)).priceToAggregate(any());
                verify(rule2, times(1)).priceToAggregate(any());
        }
}