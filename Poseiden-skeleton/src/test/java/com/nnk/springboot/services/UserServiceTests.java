package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exceptions.services.EntityNotFoundException;
import com.nnk.springboot.exceptions.user.InvalidPasswordException;
import com.nnk.springboot.exceptions.user.UserNotFoundException;
import com.nnk.springboot.exceptions.user.UsernameAlreadyExistsException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTests extends AbstractServiceTests<User, UserDTO, Integer> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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

    protected void updateTestDTO(UserDTO dto) {
        dto.setUsername( dto.getUsername() + "1" );
    }

    protected Integer getEntityId(User entity) {
        return entity.getId();
    }

    protected Integer getDTOId(UserDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(UserDTO aDto, UserDTO bDto) {
        return !aDto.getUsername().equals(bDto.getUsername());
    }

    @Override
    protected UserService getService() {
        return userService;
    }

    @Override
    protected UserRepository getRepository() { return userRepository; }

    @Test
    public void testFindByUsernameSuccess() throws Exception {
        User entity = this.getLatestStoredEntity();

        userService.findByUsername(entity.getUsername());
    }

    @Test
    public void testFindByUsernameFail() throws Exception {
        User entity = this.getLatestStoredEntity();

        assertThrows(EntityNotFoundException.class, () -> {
            userService.findByUsername(entity.getUsername() + "_");
        });
    }

    public void testCreateUserSuccess() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername() + "_");
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword(testPassword);
        dto.setRole("ADMIN");

        userService.createUser(dto);
    }

    public void testCreateUserFailAtPassword() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername() + "_");
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword("a");
        dto.setRole("ADMIN");

        assertThrows(InvalidPasswordException.class, () -> {
            userService.createUser(dto);
        });
    }

    @Test
    public void testCreateUserFailAtUsername() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername());
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword(testPassword);
        dto.setRole("ADMIN");

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.createUser(dto);
        });
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername() + "_");
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword(testPassword);
        dto.setRole("ADMIN");

        userService.updateUser(entity.getId(), dto);

        User newEntity = this.getLatestStoredEntity();
        assertNotEquals(entity.getUsername(), newEntity.getUsername());
    }

    @Test
    public void testUpdateUserFailNotFound() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername() + "_");
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword(testPassword);
        dto.setRole("ADMIN");

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(entity.getId() + 1, dto);
        });
    }

    @Test
    public void testUpdateUserFailInvalidPassword() throws Exception {
        User entity = this.getLatestStoredEntity();

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername() + "_");
        dto.setFullname(entity.getFullname() + "_");
        dto.setPassword("a");
        dto.setRole("ADMIN");

        assertThrows(InvalidPasswordException.class, () -> {
            userService.updateUser(entity.getId(), dto);
        });
    }
}
