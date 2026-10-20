
package com.mycompany.prog5121_part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Supplier;

public class MessageTest {

    @Test
    public void testRecipientCellValid() {
        Message message = new Message();
        message.setRecipient("+27764579616");
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testRecipientCellInvalid() {
        Message message = new Message();
        message.setRecipient("0764579616");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell());
    }

    @Test
    public void testMessageLengthValid() {
        Message message = new Message();
        message.setMessage("Hello Dineo");
        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthExactly250() {
        Message message = new Message();
        message.setMessage("A".repeat(250));
        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthTooLong() {
        Message message = new Message();
        message.setMessage("A".repeat(251));
        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testMessageIDIsTenDigits() {
        Message message = new Message();
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testCreateMessageHash() {
        Message message = new Message(0, "+27764579616", "HI TONIGHT");
        assertTrue(message.createMessageHash().matches("\\d{2}:0:HITONIGHT"));
    }

    @Test
    public void testCreateMessageHashExactExample() {
        Message message = new Message(
                "0012345678", 0, "+27764579616", "HI TONIGHT");
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    public void testCreateRemainingMessageHashesInLoop() {
        String[] ids = {"1112345678", "2212345678", "3312345678"};
        String[] messages = {"HI MIKE", "GOOD MORNING", "SEE YOU"};
        String[] expectedHashes = {
            "11:1:HIMIKE", "22:2:GOODMORNING", "33:3:SEEYOU"
        };

        for (int i = 0; i < ids.length; i++) {
            Message message = new Message(
                    ids[i], i + 1, "+27764579616", messages[i]);
            assertEquals(expectedHashes[i], message.createMessageHash());
        }
    }

    @Test
    public void testStoreMessageToJson() throws Exception {
        Message message = new Message(
                "0012345678", 0, "+27764579616", "HI TONIGHT");
        message.createMessageHash();

        Path path = Path.of("messages.json");
        boolean existed = Files.exists(path);
        String original = existed ? Files.readString(path) : null;

        try {
            assertEquals("Message successfully stored.",
                    message.storeMessageToJson());
            assertTrue(Files.exists(path));
            assertTrue(Files.readString(path).contains("HI TONIGHT"));
            assertEquals("Stored", message.getMessageStatus());
        } finally {
            if (existed) {
                Files.writeString(path, original);
            } else {
                Files.deleteIfExists(path);
            }
        }
    }

    @Test
    public void testSentMessageSuccessfully() {
        int before = PROG5121_Part1.returnTotalMessages();
        String output = runSentMessage(
                "1\n+27718693002\nHi Mike, can you join us for dinner tonight?\n1\n");

        assertTrue(output.contains("Message sent"));
        assertEquals(before + 1, PROG5121_Part1.returnTotalMessages());
        assertTrue(PROG5121_Part1.printMessages()
                .contains("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testDisregardMessage() {
        int before = PROG5121_Part1.returnTotalMessages();
        String output = runSentMessage(
                "1\n+27764579616\nHi Keegan, did you receive the payment?\n2\n0\n");

        assertTrue(output.contains("Press 0 to delete the message."));
        assertTrue(output.contains("Message successfully disregarded."));
        assertEquals(before, PROG5121_Part1.returnTotalMessages());
    }

    @Test
    public void testStoreMessageChoice() throws Exception {
        Path path = Path.of("messages.json");
        boolean existed = Files.exists(path);
        String original = existed ? Files.readString(path) : null;

        try {
            String output = runSentMessage(
                    "1\n+27764579616\nHi Keegan, did you receive the payment?\n3\n");

            assertTrue(output.contains("Message successfully stored."));
            assertTrue(Files.exists(path));
            assertTrue(Files.readString(path)
                    .contains("Hi Keegan, did you receive the payment?"));
        } finally {
            if (existed) {
                Files.writeString(path, original);
            } else {
                Files.deleteIfExists(path);
            }
        }
    }

    private String runSentMessage(String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try (Scanner scanner = new Scanner(new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8)));
             PrintStream capturedOut = new PrintStream(output)) {
            System.setOut(capturedOut);
            PROG5121_Part1.SentMessage(scanner);
        } finally {
            System.setOut(originalOut);
        }

        return output.toString();
    }

    @Test
    public void testMessageLengthSuccessFeedback() {
        Message message = new Message();
        message.setMessage("Hello Dineo");
        assertEquals("Message ready to send.",
                message.getMessageLengthFeedback());
    }

    @Test
    public void testMessageLengthFailureFeedback() {
        Message message = new Message();
        message.setMessage("A".repeat(255));
        assertEquals(
                "Message exceeds 250 characters by 5; please reduce the size.",
                message.getMessageLengthFeedback());
    }

    // Assignment test data for feature tests.
    private static final String ASSIGNMENT_TEST_JSON = """
            [
              {"messageID":"1000000001","messageNumber":1,"recipient":"+27834557896","message":"Did you get the cake?","messageHash":"10:1:DIDYOUGETTHECAKE","messageStatus":"Sent"},
              {"messageID":"1000000002","messageNumber":2,"recipient":"+27838884567","message":"Where are you? You are late! I have asked you to be on time.","messageHash":"10:2:WHEREAREYOU","messageStatus":"Stored"},
              {"messageID":"1000000003","messageNumber":3,"recipient":"+27834484567","message":"Yohhoooo, I am at your gate.","messageHash":"10:3:YOHOOOO","messageStatus":"Disregard"},
              {"messageID":"1000000004","messageNumber":4,"recipient":"08388884567","message":"It is dinner time!","messageHash":"10:4:ITISDINNERTIME","messageStatus":"Sent"},
              {"messageID":"1000000005","messageNumber":5,"recipient":"+27838884567","message":"Ok, I am leaving without you.","messageHash":"10:5:OKIAMLEAVING","messageStatus":"Stored"}
            ]
            """;

    private String withAssignmentTestData(Supplier<String> action)
            throws Exception {
        Path path = Path.of("messages.json");
        boolean existed = Files.exists(path);
        String original = existed ? Files.readString(path) : null;

        try {
            Files.writeString(path, ASSIGNMENT_TEST_JSON,
                    StandardCharsets.UTF_8);
            return action.get();
        } finally {
            if (existed) {
                Files.writeString(path, original, StandardCharsets.UTF_8);
            } else {
                Files.deleteIfExists(path);
            }
        }
    }

    @Test
    public void testFindLongestMessage() throws Exception {
        String result = withAssignmentTestData(
                PROG5121_Part1::findLongestMessage);

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                result);
    }

    @Test
    public void testSearchMessageByID() throws Exception {
        String result = withAssignmentTestData(
                () -> PROG5121_Part1.searchMessageByID("1000000001"));

        assertTrue(result.contains("1000000001"));
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("Sent"));
    }

    @Test
    public void testSearchMessagesByRecipient() throws Exception {
        String result = withAssignmentTestData(
                () -> PROG5121_Part1.searchMessagesByRecipient(
                        "+27838884567"));

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
        assertTrue(result.contains("Stored"));
    }

    @Test
    public void testGenerateMessageReport() throws Exception {
        String result = withAssignmentTestData(
                PROG5121_Part1::generateMessageReport);

        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Yohhoooo, I am at your gate."));
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains("Ok, I am leaving without you."));
        assertTrue(result.contains("Sent"));
        assertTrue(result.contains("Stored"));
        assertTrue(result.contains("Disregard"));
    }
}
