package com.lunchvoting.repository;

import com.lunchvoting.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.MenuTestData.*;

public class MenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void save() {
        Menu created = menuRepository.save(NEW_MENU);
        assertMatch(menuRepository.findAll(), R_1_MENU_1, R_2_MENU_1, created);
    }
}