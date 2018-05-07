package com.lunchvoting;

import com.lunchvoting.model.Menu;
import com.lunchvoting.model.MenuItem;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static com.lunchvoting.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {

    public static final Menu R_1_MENU_1 = new Menu(1, RESTAURANT1, LocalDate.now(),
            new LinkedHashSet<>(
                    Arrays.asList(new MenuItem(1, "Menu1 Item1", 300),
                            new MenuItem(2, "Menu1 Item2", 600),
                            new MenuItem(3, "Menu1 Item3", 900)))
    );

    public static final Menu R_2_MENU_1 = new Menu(2, RESTAURANT2, LocalDate.now(),
            new LinkedHashSet<>(
                    Arrays.asList(new MenuItem(4, "Menu2 Item1", 200),
                            new MenuItem(5, "Menu2 Item2", 400),
                            new MenuItem(6, "Menu2 Item3", 600)))
    );

    public static final Menu NEW_MENU = new Menu(null, RESTAURANT1, LocalDate.of(2018, 5, 6),
            new LinkedHashSet<>(
                    Arrays.asList(new MenuItem(null, "New Menu Item", 300),
                            new MenuItem(null, "New Menu Item", 600),
                            new MenuItem(null, "New Menu Item", 900)))
    );

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
