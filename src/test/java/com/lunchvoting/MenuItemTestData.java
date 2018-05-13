package com.lunchvoting;

import com.lunchvoting.model.MenuItem;

import java.util.Arrays;

import static com.lunchvoting.MenuTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuItemTestData {

    public static final MenuItem M1_ITEM_1 = new MenuItem(1, "Item1", 300, R_1_MENU_1);
    public static final MenuItem M1_ITEM_2 = new MenuItem(2, "Item2", 600, R_1_MENU_1);
    public static final MenuItem M1_ITEM_3 = new MenuItem(3, "Item3", 900, R_1_MENU_1);
    public static final MenuItem M2_ITEM_1 = new MenuItem(4, "Item1", 200, R_2_MENU_1);
    public static final MenuItem M2_ITEM_2 = new MenuItem(5, "Item2", 400, R_2_MENU_1);
    public static final MenuItem M2_ITEM_3 = new MenuItem(6, "Item3", 600, R_2_MENU_1);

    public static MenuItem getNew() {
        return new MenuItem(null, "New item", 950, R_1_MENU_1);
    }

    public static void assertMatch(Iterable<MenuItem> actual, MenuItem... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<MenuItem> actual, Iterable<MenuItem> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }
}
