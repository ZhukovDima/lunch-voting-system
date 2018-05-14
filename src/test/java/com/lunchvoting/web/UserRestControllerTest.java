package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.model.Restaurant;
import com.lunchvoting.model.Role;
import com.lunchvoting.model.User;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.lunchvoting.TestUtil.contentJson;
import static com.lunchvoting.TestUtil.contentJsonArray;
import static com.lunchvoting.TestUtil.userHttpBasic;
import static com.lunchvoting.UserTestData.*;
import static com.lunchvoting.UserTestData.USER1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = UserRestController.REST_URL + "/";

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New user", "newuser@mail.com", "123456", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(expected)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(new User(null, "New user", "newuser@mail.com", "123456", Role.ROLE_USER))))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(new User(null, "New user", "newuser@mail.com", "123456", Role.ROLE_USER))))
            .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("Updated name");
        mockMvc.perform(put(REST_URL + USER_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(USER1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJsonArray(USER1, USER2, ADMIN));
    }
}