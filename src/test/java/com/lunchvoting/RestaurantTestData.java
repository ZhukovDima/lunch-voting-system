package com.lunchvoting;

import com.lunchvoting.model.Restaurant;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public static final int RESTAURANT_1_ID = 1;
    public static final int RESTAURANT_2_ID = 2;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_1_ID, "Restaurant1");

    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_2_ID, "Restaurant2");

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus").isEqualTo(expected);
    }
}
