package com.lunchvoting.web;

import com.lunchvoting.MenuTestData;
import com.lunchvoting.TestUtil;
import org.junit.Test;

import static com.lunchvoting.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = VoteRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post(REST_URL + MenuTestData.R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(USER2)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL + MenuTestData.R_1_MENU_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL + MenuTestData.R_1_MENU_1_ID)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetCurrent() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetCurrentUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void testGetCurrentForbiden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden());
    }
}