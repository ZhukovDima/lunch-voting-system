package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.model.Menu;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.RestaurantTestData.RESTAURANT_1_ID;
import static com.lunchvoting.TestUtil.contentJson;
import static com.lunchvoting.TestUtil.contentJsonArray;
import static com.lunchvoting.UserTestData.ADMIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = MenuRestController.REST_URL + "/";
    static final String RESTAURANT_MENU_REST_URL = "/rest/restaurants/" + RESTAURANT_1_ID + "/menus/";

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testCreate() throws Exception {
        Menu expected = getNew();
        ResultActions action = mockMvc.perform(post(RESTAURANT_MENU_REST_URL)
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Menu returned = TestUtil.readFromJson(action, Menu.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(menuRepository.getAllByDate(LocalDate.now().plus(1, ChronoUnit.DAYS)), returned);
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(RESTAURANT_MENU_REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = new Menu(R_1_MENU_1);
        updated.setDateEntered(LocalDate.now().plus(1, ChronoUnit.DAYS));
        ResultActions action = mockMvc.perform(put(RESTAURANT_MENU_REST_URL + R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        Menu returned = TestUtil.readFromJson(action, Menu.class);
        assertMatch(returned, updated);
    }

    @Test
    public void testUpdateUnauth() throws Exception {
        mockMvc.perform(put(RESTAURANT_MENU_REST_URL + R_1_MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(UPDATED_MENU)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(RESTAURANT_MENU_REST_URL + R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuRepository.getAllByDate(LocalDate.now()), R_2_MENU_1);
    }

    @Test
    public void testDeleteUnauth() throws Exception {
        mockMvc.perform(delete(RESTAURANT_MENU_REST_URL + R_1_MENU_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetAllCurrentDate() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJsonArray(R_1_MENU_1_WITH_ITEMS, R_2_MENU_1_WITH_ITEMS));
    }

    @Test
    public void testGetAllByDate() throws Exception {
        Menu updated = new Menu(R_1_MENU_1_WITH_ITEMS);
        updated.setDateEntered(LocalDate.of(2018, 5, 10));
        menuRepository.save(updated);
        mockMvc.perform(get(REST_URL)
                .param("date", "2018-05-10")
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJsonArray(updated));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(RESTAURANT_MENU_REST_URL + R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(R_1_MENU_1_WITH_ITEMS));
    }

    @Test
    public void testGetCurrentByRestaurantId() throws Exception {
        mockMvc.perform(get(RESTAURANT_MENU_REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(R_1_MENU_1_WITH_ITEMS));
    }
}