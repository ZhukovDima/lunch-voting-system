package com.lunchvoting.web.vote;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.VoteTestData;
import com.lunchvoting.to.VoteTo;
import com.lunchvoting.web.AbstractControllerTest;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = VoteRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        VoteTo createdTo = VoteTestData.getNew();
        mockMvc.perform(post(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.USER2))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());
    }
}