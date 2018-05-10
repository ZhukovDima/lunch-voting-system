package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.model.Restaurant;
import com.lunchvoting.repository.RestaurantRepository;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.lunchvoting.RestaurantTestData.*;
import static com.lunchvoting.TestUtil.*;
import static com.lunchvoting.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = RestaurantRestController.REST_URL + "/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = getNew();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(expected)))
            .andExpect(status().isCreated());
        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(restaurantRepository.findAll(), RESTAURANT1, RESTAURANT2, returned);
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJsonArray(RESTAURANT1, RESTAURANT2)));

    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1_ID)
            .with(userHttpBasic(USER1)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(contentJson(RESTAURANT1));
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("Updated restaurant");
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepository.findAll(), RESTAURANT2);
    }
}