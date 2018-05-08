package com.lunchvoting.web.menu;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.web.AbstractControllerTest;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.lunchvoting.MenuTestData.NEW_MENU;
import static com.lunchvoting.RestaurantTestData.RESTAURANT_1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/rest/admin/restaurants/" + RESTAURANT_1_ID + "/menus";

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(NEW_MENU)))
                .andExpect(status().isCreated());
    }
}