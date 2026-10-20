
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MessageTest {

    // Part 1: Recipient validation

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

    // Part 1: Message length

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

    // Part 1: Message ID

    @Test
    public void testMessageIDIsTenDigits() {
        Message message = new Message();

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    // Part 1: Message hash

    @Test
    public void testCreateMessageHash() {
        Message message = new Message(
                0, "+27764579616", "HI TONIGHT");

        String hash = message.createMessageHash();

        assertTrue(hash.matches("\\d{2}:0:HITONIGHT"));
    }

    @Test
    public void testCreateMessageHashExactExample() {
        Message message = new Message(
                "0012345678", 0, "+27764579616", "HI TONIGHT");

        assertEquals("00:0:HITONIGHT",
                message.createMessageHash());
    }

    @Test
    public void testCreateRemainingMessageHashesInLoop() {
        String[] ids = {
            "1112345678",
            "2212345678",
            "3312345678"
        };

        String[] messages = {
            "HI MIKE",
            "GOOD MORNING",
            "SEE YOU"
        };

        String[] expectedHashes = {
            "11:1:HIMIKE",
            "22:2:GOODMORNING",
            "33:3:SEEYOU"
        };

        for (int i = 0; i < ids.length; i++) {
            Message message = new Message(
                    ids[i], i + 1, "+27764579616", messages[i]);

            assertEquals(expectedHashes[i],
                    message.createMessageHash());
        }
    }

    // Part 1: JSON storage

    @Test
    public void testStoreMessageToJson() throws Exception {
        Message message = new Message(
                "0012345678", 0, "+27764579616", "HI TONIGHT");

        message.createMessageHash();

        Path path = Path.of("messages.json");
        boolean fileExistedBefore = Files.exists(path);
        String originalContent = fileExistedBefore
                ? Files.readString(path) : null;

        try {
            assertEquals("Message successfully stored.",
                    message.storeMessageToJson());

            assertTrue(Files.exists(path));
            assertTrue(Files.readString(path).contains("HI TONIGHT"));
            assertEquals("Stored", message.getMessageStatus());
        } finally {
            if (fileExistedBefore) {
                Files.writeString(path, originalContent);
            } else {
                Files.deleteIfExists(path);
            }
        }
    }

    // Part 2: Send message

    @Test
    public void testSentMessageSuccessfully() {
        int countBefore = PROG5121_Part1.returnTotalMessages();

        String output = runSentMessage(
                "1\n+27718693002\nHi Mike, can you join us for dinner tonight?\n1\n");

        assertTrue(output.contains("Message sent"));
          assertEquals(countBefore + 1,
                PROG5121_Part1.returnTotalMessages());
        assertTrue(PROG5121_Part1.printMessages()
                .contains("Hi Mike, can you join us for dinner tonight?"));
    }

    // Part 2: Disregard message

    @Test
    public void testDisregardMessage() {
        int countBefore = PROG5121_Part1.returnTotalMessages();

        String output = runSentMessage(
                "1\n+27764579616\nHi Keegan, did you receive the payment?\n2\n0\n");

        assertTrue(output.contains("Press 0 to delete the message."));
        assertTrue(output.contains("Message successfully disregarded."));
        assertEquals(countBefore,
                PROG5121_Part1.returnTotalMessages());
    }

    // Part 2: Store message

    @Test
    public void testStoreMessageChoice() throws Exception {
        Path path = Path.of("messages.json");
        boolean fileExistedBefore = Files.exists(path);
        String originalContent = fileExistedBefore
                ? Files.readString(path) : null;

        try {
            String output = runSentMessage(
                    "1\n+27764579616\nHi Keegan, did you receive the payment?\n3\n");

            assertTrue(output.contains("Message successfully stored."));
            assertTrue(Files.exists(path));
            assertTrue(Files.readString(path)
                    .contains("Hi Keegan, did you receive the payment?"));
        } finally {
            if (fileExistedBefore) {
                Files.writeString(path, originalContent);
            } else {
                Files.deleteIfExists(path);
            }
        }
    }

    // Helper: run the interactive method with simulated input

    private String runSentMessage(String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try (Scanner scanner = new Scanner(
                    new ByteArrayInputStream(
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

    assertEquals(
            "Message ready to send.",
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


@Test

public void testFindLongestMessage() {
    String result = PROG5121_Part1.findLongestMessage();

    assertTrue(result != null);
    assertFalse(result.isEmpty());
}

@Test
public void testSearchMessageByID() {
    String result = PROG5121_Part1.searchMessageByID("4474007213");

    assertTrue(result.contains("Hi thuli"));
    assertTrue(result.contains("Stored"));
}

@Test
public void testSearchMessagesByRecipient() {
    String result = PROG5121_Part1.searchMessagesByRecipient(
            "+27796765643");

    assertTrue(result.contains("hi mom"));
    assertTrue(result.contains("Stored"));
}

@Test
public void testGenerateMessageReport() {
    String result = PROG5121_Part1.generateMessageReport();

    assertTrue(result.contains("Hi thuli"));
    assertTrue(result.contains("hi mom"));
    assertTrue(result.contains("Stored"));
}

}
