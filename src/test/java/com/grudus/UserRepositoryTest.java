package com.grudus;

import com.grudus.dao.UserRepository;
import com.grudus.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MirApplication.class)
public class UserRepositoryTest {

	private final String USER_LOGIN = "Kuba";
	private final String USER_PASSWORD = "admin";
	private final String USER_EMAIL = "Kuba@kuba.com";


	private User user;


	@Autowired
	private UserRepository userRepository;



	@Before
	public void init() {
		user = new User(USER_LOGIN, USER_PASSWORD, USER_EMAIL);

		userRepository.save(user);
		userRepository.save(new User("anon", "anonanon", "email@email.com"));
	}


	@Test
	public void userFindByLogin_ShouldReturnValidUser() {
		User newUser = this.userRepository.findByLogin(USER_LOGIN).get();
		assertEquals(newUser, user);
	}

	@Test
	public void userFindByLogin_ShouldNotFindAnyUser() {
		Optional<User> newUser = this.userRepository.findByLogin("SomeRandom");
		assertTrue(!newUser.isPresent());
	}


	@Test
	public void userFindById_ShouldReturnValidUser() {
		User newUser = this.userRepository.findOne(user.get_id());
		assertEquals(newUser, user);
	}

	@Test
	public void userFindById_ShouldNotFindAnyUser() {
		User newUser = this.userRepository.findOne("notAnId");
		assertNull(newUser);
	}

	@Test
	public void userDeleteByLogin_ShouldSuccessfullyDelete() {
		final double databaseSize = this.userRepository.count();
		this.userRepository.deleteByLogin(USER_LOGIN);
		assertFalse(this.userRepository.findAll().contains(user));
		assertNotEquals(databaseSize, this.userRepository.count());
	}

	@Test
	public void userDeleteByLogin_wrongLogin_ShouldNotDeleteAnyData() {
		final double databaseSize = this.userRepository.count();
		this.userRepository.deleteByLogin("SomeRandom");
		assertEquals(databaseSize, this.userRepository.count(), 0);
	}


	@After
	public void clean() {
		user = null;
		userRepository.deleteAll();
	}


}
