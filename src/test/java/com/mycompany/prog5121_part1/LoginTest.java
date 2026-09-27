package com.mycompany.prog5121_part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyl_1");

        assertTrue(login.checkUserName());
    }

    @Test
    void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!");

        assertFalse(login.checkUserName());
    }

    @Test
    void testPasswordMeetsComplexityRequirements() {
        Login login = new Login();
        login.setPassword("Ch&sec@ke99!");

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testPasswordDoesNotMeetComplexityRequirements() {
        Login login = new Login();
        login.setPassword("password");

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    void testCellPhoneCorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("+27838968976");

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("08966553");

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    void testLoginUserSuccessfully() {
        Login login = new Login();

        login.setFirstName("Dineo");
        login.setLastName("Lesabe");
        login.setUsername("kyl_1");
        login.setPassword("Ch&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");

        login.registerUser();

        assertTrue(login.loginUser());
    }

    @Test
    void testLoginUserUnsuccessfully() {
        Login login = new Login();

        login.setFirstName("Dineo");
        login.setLastName("Lesabe");
        login.setUsername("kyl_1");
        login.setPassword("Ch&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");

        login.registerUser();

        login.setUsername("wrong");
        login.setPassword("wrong");

        assertFalse(login.loginUser());
    }

    @Test
    void testSuccessfulLoginStatus() {
        Login login = new Login();

        login.setFirstName("Dineo");
        login.setLastName("Lesabe");
        login.setUsername("kyl_1");
        login.setPassword("Ch&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");

        login.registerUser();

        assertEquals(
                "Welcome Dineo, Lesabe it is great to see you again.",
                login.returnLoginStatus()
        );
    }

    @Test
    void testUnsuccessfulLoginStatus() {
        Login login = new Login();

        login.setFirstName("Dineo");
        login.setLastName("Lesabe");
        login.setUsername("kyl_1");
        login.setPassword("Ch&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");

        login.registerUser();

        login.setUsername("wrong");
        login.setPassword("wrong");

        assertEquals(
                "Username or password incorrect, please try again.",
                login.returnLoginStatus()
        );
    }
}