package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.lunchvoting.RestaurantTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = RestaurantRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post(REST_URL)
            .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.writeValue(getNew())))
            .andExpect(status().isCreated());
    }
}