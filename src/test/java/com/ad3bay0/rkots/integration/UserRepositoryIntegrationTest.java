package com.ad3bay0.rkots.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    private final String TEST_USER_NAME = "tester";
    private final String TEST_USER_PASSWORD = "tester";
  


    @Test
	void givenEmptyDBWhenFindByUsernameThenReturnEmptyOptional() {

        Optional<User> foundUser = userRepository.findByUsername(TEST_USER_NAME);

        assertThat(foundUser.isPresent()).isEqualTo(false);
    }
    
    @Test
    public void givenUserInDBWhenFindOneByNameThenReturnOptionalWithUser() {
        User user = new User();
        user.setUsername(TEST_USER_NAME);
        user.setPassword(TEST_USER_PASSWORD);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(TEST_USER_NAME);

        assertThat(foundUser.isPresent()).isEqualTo(true);

        assertThat(foundUser
          .get()
          .getUsername()).isEqualTo(TEST_USER_NAME);
    }
    
}