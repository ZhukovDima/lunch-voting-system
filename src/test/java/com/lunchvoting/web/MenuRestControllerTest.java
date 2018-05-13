package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.lunchvoting.MenuTestData.UPDATED_MENU;
import static com.lunchvoting.MenuTestData.getNew;
import static com.lunchvoting.RestaurantTestData.RESTAURANT_1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = MenuRestController.REST_URL + "/";
    static final String RESTAURANT_MENU_REST_URL = "/rest/restaurants/" + RESTAURANT_1_ID + "/menus";

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post(RESTAURANT_MENU_REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put(RESTAURANT_MENU_REST_URL + "/1")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(UPDATED_MENU)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCurrent() throws Exception {
        mockMvc.perform(get(REST_URL)
                .param("date", "2018-05-12")
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8));
    }
}