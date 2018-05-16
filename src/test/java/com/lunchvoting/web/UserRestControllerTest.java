package com.lunchvoting.web;

import com.lunchvoting.TestUtil;
import com.lunchvoting.UserTestData;
import com.lunchvoting.model.User;
import com.lunchvoting.repository.UserRepository;
import com.lunchvoting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserRepository userRepository;

    @Test
    public void testCreate() throws Exception {
        User expected = getNew();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(UserTestData.jsonWithPassword(expected, "newPass")))
            .andExpect(status().isCreated());
        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(userRepository.findAll(), USER1, USER2, ADMIN, returned);
    }

    @Test
    public void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(getNew())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(getNew())))
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
        assertMatch(userRepository.findAll(), USER2, ADMIN);
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