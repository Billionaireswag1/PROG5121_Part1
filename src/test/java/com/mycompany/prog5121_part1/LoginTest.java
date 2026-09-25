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
}