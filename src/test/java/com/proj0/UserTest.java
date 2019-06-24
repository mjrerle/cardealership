package com.proj0;

import org.junit.Test;

import static org.junit.Assert.*;

import com.proj0.models.User;
import com.proj0.services.UserService;

/**
 * Unit test for simple App.
 */
public class UserTest {

    @Test
    public void userSanityTest() {
        assertTrue(true);
    }

    @Test
    public void aUserCanLogin() {
        // given a user
        User admin = new User("admin", "password", "admin");
        // they can login with username and password
        User u = UserService.login(admin.getUsername(), admin.getPassword());
        assertTrue(u.getUsername().equals("admin"));
    }

    @Test
    public void anUnknownUserCannotLogin() {
        assertNull(UserService.login("username", "pass"));
    }

    @Test
    public void aUserCanBeCreated() {
        User u = new User("timmy", "pass", "anonymous");
        assertTrue(UserService.register(u));
        UserService.removeUser("timmy");
    }

    @Test
    public void aUserCanBeDeleted() {
        User u = new User("johnny", "pass", "anonymous");
        UserService.register(u);
        assertTrue(UserService.removeUser(u.getUsername()));
    }

    @Test
    public void anUnknownUserCannotBeDeleted() {
        assertFalse(UserService.removeUser("xyz"));
    }

    @Test
    public void anEmployeeCanGetAUser() {
        // they can get a customer by username
        User user = UserService.getUser("admin");
        assertTrue(user.getUsername().equals("admin"));
        assertTrue(user.getPassword().equals("password"));
        assertTrue(user.getRole().equals("admin"));
    }

    @Test
    public void anEmployeeCannotGetAnUnknownUser() {
        // they can get a customer by username
        User another = UserService.getUser("null");
        assertNull(another);
    }

}
