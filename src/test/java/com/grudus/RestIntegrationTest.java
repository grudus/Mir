package com.grudus;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.entities.Message;
import com.grudus.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MirApplication.class)
@WebAppConfiguration
public class RestIntegrationTest {


    private MockMvc mockMvc;

    private User user;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Before
    public void init() {
        user = new User("Kuba", passwordEncoder.encode("admin"), "kuba@kuba.com");

        userRepository.save(Arrays.asList(
                user,
                new User("Marcin", passwordEncoder.encode("marcin"), "marcin@marcin.com"),
                new User("Gospel", passwordEncoder.encode("helikopter"), "google@gmail.com")
        ));

        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getLogin_WithWrongLogin_ShouldNotFoundAnyUser() throws Exception {
        mockMvc.perform(get("/SomeRandom")).
                andExpect(status().isNotFound());
    }

    @Test
    public void userIsFound() throws Exception {
        mockMvc.perform(get("/Kuba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", is("Kuba")))
                .andExpect(jsonPath("$.logged", is(false)))
                .andExpect(jsonPath("$.messages", is(Collections.EMPTY_LIST)));
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].login", is("Kuba")));
    }



    @After()
    public void delete() {
        mockMvc = null;
        user = null;
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

}
