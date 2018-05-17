package com.lunchvoting.web;

import com.lunchvoting.MenuItemTestData;
import com.lunchvoting.TestUtil;
import com.lunchvoting.model.MenuItem;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.lunchvoting.MenuItemTestData.*;
import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.RestaurantTestData.*;
import static com.lunchvoting.UserTestData.ADMIN;
import static com.lunchvoting.UserTestData.USER1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuItemRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/restaurants/" + RESTAURANT_1_ID + "/menus/" + R_1_MENU_1_ID + "/items/";

    @Test
    public void testCreate() throws Exception {
        MenuItem expected = MenuItemTestData.getNew();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        MenuItem returned = TestUtil.readFromJson(action, MenuItem.class);
        expected.setId(returned.getId());
        MenuItemTestData.assertMatch(returned, expected);
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(MenuItemTestData.getNew())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(TestUtil.userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(MenuItemTestData.getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        MenuItem updated = new MenuItem(M1_ITEM_1);
        updated.setName("updated");
        mockMvc.perform(put(REST_URL + M1_ITEM_1_ID)
                .with(TestUtil.userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUnauth() throws Exception {
        mockMvc.perform(put(REST_URL + M1_ITEM_1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(M1_ITEM_1)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateForbidden() throws Exception {
        mockMvc.perform(put(REST_URL + M1_ITEM_1_ID)
                .with(TestUtil.userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(M1_ITEM_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + M1_ITEM_1_ID)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUnauth() throws Exception {
        mockMvc.perform(delete(REST_URL + M1_ITEM_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL + M1_ITEM_1_ID)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }
}