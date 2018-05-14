package com.lunchvoting;

import com.lunchvoting.model.Menu;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static com.lunchvoting.MenuItemTestData.*;
import static com.lunchvoting.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {

    public static final int R_1_MENU_1_ID = 1;

    public static final Menu R_1_MENU_1 = new Menu(R_1_MENU_1_ID, RESTAURANT1, LocalDate.now());
    public static final Menu R_2_MENU_1 = new Menu(R_1_MENU_1_ID + 1, RESTAURANT2, LocalDate.now());
    public static final Menu R_1_MENU_1_WITH_ITEMS = new Menu(R_1_MENU_1_ID, RESTAURANT1, LocalDate.now(), M1_ITEM_1, M1_ITEM_2, M1_ITEM_3);
    public static final Menu R_2_MENU_1_WITH_ITEMS = new Menu(R_1_MENU_1_ID + 1, RESTAURANT2, LocalDate.now(), M2_ITEM_1, M2_ITEM_2, M2_ITEM_3);
    public static final Menu UPDATED_MENU = new Menu(1, null, LocalDate.of(2018, 5, 6));

    public static Menu getNew() {
        return new Menu(null, RESTAURANT1, LocalDate.now().plus(1, ChronoUnit.DAYS));
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "items", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("items", "restaurant").isEqualTo(expected);
    }
}
