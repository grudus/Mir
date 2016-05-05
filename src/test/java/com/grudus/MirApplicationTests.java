package com.grudus;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MirApplication.class)
@WebAppConfiguration
public class MirApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void init() {
		userRepository.deleteAll();
		messageRepository.deleteAll();

		userRepository.save(Arrays.asList(
				new User[]{ new User("Kuba", passwordEncoder.encode("admin"), "kuba@kuba.com"),
				new User("Marcin", passwordEncoder.encode("marcin"), "marcin@marcin.com")}
		));

	}

}
