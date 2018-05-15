package com.lunchvoting.web;

import com.lunchvoting.MenuTestData;
import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = VoteRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post(REST_URL + MenuTestData.R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER2)))
                .andExpect(status().isCreated());
    }
}