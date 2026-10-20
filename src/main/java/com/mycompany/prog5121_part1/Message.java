package com.mycompany.prog5121_part1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a QuickChat message and provides message validation,
 * hash generation and JSON storage functionality.
 *
 * @author Dineo Lesabe
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String messageStatus;

    public Message() {
        this.messageID = generateMessageID();
        this.messageStatus = "Pending";
    }

    public Message(int messageNumber, String recipient, String message) {
        this();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
    }

    public Message(String messageID, int messageNumber,
                   String recipient, String message) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageStatus = "Pending";
    }

    public String generateMessageID() {
        long id = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{10}");
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public boolean checkMessageLength() {
        return message != null && message.length() <= 250;
    }

    public String getMessageLengthFeedback() {
        if (message == null) {
            return "Please enter a message.";
        }

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int excessCharacters = message.length() - 250;

        return "Message exceeds 250 characters by " + excessCharacters
                + "; please reduce the size.";
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2
                || message == null || message.trim().isEmpty()) {
            return "";
        }

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        messageHash = messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord.toUpperCase()
                + lastWord.toUpperCase();

        return messageHash;
    }

      /**
 * Stores message data in JSON format.
 * JSON structure: JSON.org (n.d.).
 * Java file operations: Oracle (2026).
 */
    public String storeMessageToJson() {
        Path path = Path.of("messages.json");

        try {
            String existingJson = Files.exists(path)
                    ? Files.readString(path, StandardCharsets.UTF_8)
                    : "[]";

            existingJson = existingJson.trim();

            if (!existingJson.startsWith("[")
                    || !existingJson.endsWith("]")) {
                return "Unable to store message: invalid JSON file.";
            }

            String existingMessages =
                    existingJson.substring(1, existingJson.length() - 1).trim();

            String messageJson = "{"
                    + "\"messageID\":\"" + escapeJson(messageID) + "\","
                    + "\"messageNumber\":" + messageNumber + ","
                    + "\"recipient\":\"" + escapeJson(recipient) + "\","
                    + "\"message\":\"" + escapeJson(message) + "\","
                    + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                    + "\"messageStatus\":\"Stored\""
                    + "}";

            String updatedJson = "["
                    + (existingMessages.isEmpty()
                    ? ""
                    : existingMessages + ",")
                    + messageJson + "]";

            Files.writeString(path, updatedJson, StandardCharsets.UTF_8);
            messageStatus = "Stored";

            return "Message successfully stored.";

        } catch (IOException e) {
            return "Unable to store message: " + e.getMessage();
        }
    }

    public String storeMessage() {
        return storeMessageToJson();
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }
}
