package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests extends AbstractControllerTests<User, Integer> {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String testPassword = "gvk{pT2=5Kn]S<.*$AXtJ:";

    @Override
    protected User generateTestEntity() {
        User user = new User();
        user.setUsername("Test");
        user.setFullname("Test");
        user.setPassword("$2y$10$Y5Fkc6P9x65ut8uZekOr/.NDrvPkWte0CkbH05QqfIl1VdjUEiQue");
        user.setRole("ADMIN");

        return user;
    }

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return this.userRepository;
    }

    @Test
    public void testGetHomePage() throws Exception {
        mockMvc.perform(get("/user/list").with(getTestUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void testGetAddPage() throws Exception {
        mockMvc.perform(get("/user/add").with(getTestUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void testGetUpdatePage() throws Exception {
        User entity = getEntity();
        Integer id = entity.getId();

        mockMvc.perform(get("/user/update/" + id).with(getTestUser()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", Matchers.hasProperty("username", Matchers.equalTo(entity.getUsername()))));
    }

    @Test
    public void testPostAddSuccess() throws Exception {
        long beforeSize = userRepository.count();

        mockMvc.perform(post("/user/validate").with(csrf())
                        .with(getTestUser())
                        .param("username", "TestUser_")
                        .param("fullname", "TestUser_")
                        .param("password", testPassword)
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertNotEquals(beforeSize, userRepository.count());
    }

    @Test
    public void testPostAddFails() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf())
                        .with(getTestUser())
                        .param("username", "")
                        .param("fullname", "Test")
                        .param("password", testPassword)
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "username"));
    }

    @Test
    public void testPostUpdateSuccess() throws Exception {
        User entity = getEntity();
        Integer id = entity.getId();

        String newUsername = entity.getUsername() + "_";

        mockMvc.perform(post("/user/update/" + id).with(csrf())
                        .with(getTestUser())
                        .param("username", newUsername)
                        .param("fullname", "Test")
                        .param("password", testPassword)
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        User updatedTrade = getEntity();
        assertThat(updatedTrade.getUsername()).isEqualTo(newUsername);
    }

    @Test
    public void testPostUpdateFails() throws Exception {
        User entity = getEntity();
        Integer id = entity.getId();

        mockMvc.perform(post("/user/update/" + id).with(csrf())
                        .with(getTestUser())
                        .param("username", "")
                        .param("fullname", "Test")
                        .param("password", testPassword)
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "username"));
    }

    @Test
    public void testGetDelete() throws Exception {
        long beforeSize = userRepository.count();

        assertNotEquals(beforeSize, 0);

        User entity = getEntity();
        Integer id = entity.getId();

        mockMvc.perform(get("/user/delete/" + id)
                        .with(getTestUser()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertNotEquals(beforeSize, userRepository.count());
    }
}
