package com.lunchvoting.web.restaurant;

import com.lunchvoting.repository.RestaurantRepository;
import com.lunchvoting.web.AbstractControllerTest;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = ProfileRestController.REST_URL + "/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testGetAllWithCurrentMenu() throws Exception {
            mockMvc.perform(get(REST_URL))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(JsonUtil.writeValue(restaurantRepository.getAllWithCurrentMenu())));
    }
}