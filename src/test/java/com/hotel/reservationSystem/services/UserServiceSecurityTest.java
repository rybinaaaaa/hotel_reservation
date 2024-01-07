package com.hotel.reservationSystem.services;
import com.hotel.reservationSystem.models.User;
import environment.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceSecurityTest {

    @Autowired
    private UserService userService;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFindAllWithAdminRole() {
        User user = Generator.generateUser();
        userService.save(user);
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindAllWithUserRole() {
        // a regular user tries to access private method
        assertThrows(AccessDeniedException.class, () -> userService.findAll());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFindAllWithPagingAndAdminRole() {
        int page = 0;
        int size = 10;
        User user = Generator.generateUser();
        userService.save(user);
        List<User> users = userService.findAll(page, size);
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindAllWithPagingAndUserRole() {
        assertThrows(AccessDeniedException.class, () -> userService.findAll(0, 10));
    }
}
