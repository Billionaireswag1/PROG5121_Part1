
package com.mycompany.prog5121_part1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs the QuickChat application.
 *
 * @author Dineo Lesabe
 */
public class PROG5121_Part1 {

    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> allMessages = new ArrayList<>();

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No sent messages yet.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : sentMessages) {
            output.append("Message ID: ").append(message.getMessageID())
                    .append(System.lineSeparator())
                    .append("Message Hash: ").append(message.getMessageHash())
                    .append(System.lineSeparator())
                    .append("Recipient: ").append(message.getRecipient())
                    .append(System.lineSeparator())
                    .append("Message: ").append(message.getMessage())
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    /*
     * Loads messages from messages.json and combines them with
     * messages from the current session, avoiding duplicate IDs.
     */
    private static ArrayList<Message> getFeatureMessages() {
        ArrayList<Message> messages = new ArrayList<>(allMessages);
        Path path = Path.of("messages.json");

        if (!Files.exists(path)) {
            return messages;
        }

        try {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Pattern objectPattern = Pattern.compile("\\{([^{}]*)\\}");
            Matcher objectMatcher = objectPattern.matcher(json);

            while (objectMatcher.find()) {
                String object = objectMatcher.group(1);

                String id = getJsonValue(object, "messageID");
                String recipient = getJsonValue(object, "recipient");
                String text = getJsonValue(object, "message");
                String hash = getJsonValue(object, "messageHash");
                String status = getJsonValue(object, "messageStatus");
                String number = getJsonValue(object, "messageNumber");

                if (id == null || recipient == null
                        || text == null || number == null) {
                    continue;
                }

                boolean duplicate = false;

                for (Message existing : messages) {
                    if (id.equals(existing.getMessageID())) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    Message saved = new Message(
                            id, Integer.parseInt(number), recipient, text);
                    saved.setMessageHash(hash);
                    saved.setMessageStatus(status);
                    messages.add(saved);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not read saved messages.");
        }

        return messages;
    }

    private static String getJsonValue(String object, String key) {
        Pattern pattern = Pattern.compile(
                "\"" + Pattern.quote(key)
                + "\"\\s*:\\s*(?:\"((?:\\\\.|[^\"\\\\])*)\"|(\\d+))");

        Matcher matcher = pattern.matcher(object);

        if (!matcher.find()) {
            return null;
        }

        if (matcher.group(1) == null) {
            return matcher.group(2);
        }

        return unescapeJson(matcher.group(1));
    }

    private static String unescapeJson(String value) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (c != '\\' || i + 1 >= value.length()) {
                result.append(c);
                continue;
            }

            char next = value.charAt(++i);

            switch (next) {
                case '"': result.append('"'); break;
                case '\\': result.append('\\'); break;
                case '/': result.append('/'); break;
                case 'b': result.append('\b'); break;
                case 'f': result.append('\f'); break;
                case 'n': result.append('\n'); break;
                case 'r': result.append('\r'); break;
                case 't': result.append('\t'); break;
                case 'u':
                    if (i + 4 < value.length()) {
                        try {
                            result.append((char) Integer.parseInt(
                                    value.substring(i + 1, i + 5), 16));
                            i += 4;
                        } catch (NumberFormatException e) {
                            result.append("\\u");
                        }
                    } else {
                        result.append("\\u");
                    }
                    break;
                default:
                    result.append('\\').append(next);
            }
        }

        return result.toString();
    }

    /** Feature 1: Finds the longest message. */
    public static String findLongestMessage() {
        ArrayList<Message> messages = getFeatureMessages();

        if (messages.isEmpty()) {
            return "No messages available.";
        }

        Message longest = null;

        for (Message current : messages) {
            if (current.getMessage() != null
                    && (longest == null
                    || current.getMessage().length()
                    > longest.getMessage().length())) {
                longest = current;
            }
        }

        return longest == null ? "No messages available."
                : longest.getMessage();
    }

    /** Feature 2: Searches by message ID. */
    public static String searchMessageByID(String messageID) {
        for (Message current : getFeatureMessages()) {
            if (current.getMessageID().equals(messageID)) {
                return "Message ID: " + current.getMessageID()
                        + System.lineSeparator()
                        + "Recipient: " + current.getRecipient()
                        + System.lineSeparator()
                        + "Message: " + current.getMessage()
                        + System.lineSeparator()
                        + "Status: " + current.getMessageStatus();
            }
        }

        return "Message not found.";
    }

    /** Feature 3: Searches messages by recipient. */
    public static String searchMessagesByRecipient(String recipient) {
        StringBuilder output = new StringBuilder();

        for (Message current : getFeatureMessages()) {
            if (recipient.equals(current.getRecipient())) {
                if (output.length() > 0) {
                    output.append(System.lineSeparator());
                }

                output.append("Message ID: ")
                        .append(current.getMessageID())
                        .append(System.lineSeparator())
                        .append("Message: ")
                        .append(current.getMessage())
                        .append(System.lineSeparator())
                        .append("Status: ")
                        .append(current.getMessageStatus());
            }
        }

        return output.length() == 0
                ? "No messages found for this recipient."
                : output.toString();
    }

    /** Feature 4: Generates a report of all messages. */
    public static String generateMessageReport() {
        ArrayList<Message> messages = getFeatureMessages();

        if (messages.isEmpty()) {
            return "No messages available.";
        }

        StringBuilder report = new StringBuilder();

        for (Message current : messages) {
            report.append("Message ID: ")
                    .append(current.getMessageID())
                    .append(System.lineSeparator())
                    .append("Message Hash: ")
                    .append(current.getMessageHash())
                    .append(System.lineSeparator())
                    .append("Recipient: ")
                    .append(current.getRecipient())
                    .append(System.lineSeparator())
                    .append("Message: ")
                    .append(current.getMessage())
                    .append(System.lineSeparator())
                    .append("Status: ")
                    .append(current.getMessageStatus())
                    .append(System.lineSeparator())
                    .append("--------------------")
                    .append(System.lineSeparator());
        }

        return report.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("Welcome to QuickChat.");
        System.out.println("=== Registration ===");

        System.out.print("Enter your first name: ");
        login.setFirstName(scanner.nextLine());

        System.out.print("Enter your last name: ");
        login.setLastName(scanner.nextLine());

        System.out.print("Enter your username: ");
        login.setUsername(scanner.nextLine());

        System.out.print("Enter your password: ");
        login.setPassword(scanner.nextLine());

        System.out.print("Enter your cell phone number: ");
        login.setCellPhoneNumber(scanner.nextLine());

        String registrationMessage = login.registerUser();

        if (login.checkUserName()
                && login.checkPasswordComplexity()
                && login.checkCellPhoneNumber()) {

            System.out.println("Username successfully captured.");
            System.out.println("Password successfully captured.");
            System.out.println("Cell phone number successfully added.");
            System.out.println(registrationMessage);

            System.out.println("\n=== Login ===");

            System.out.print("Enter your username: ");
            login.setUsername(scanner.nextLine());

            System.out.print("Enter your password: ");
            login.setPassword(scanner.nextLine());

            System.out.println(login.returnLoginStatus());

            if (!login.loginUser()) {
                scanner.close();
                return;
            }

            int choice;

            do {
                System.out.println("\n=== Messaging Menu ===");
                System.out.println("1. Send Messages");
                System.out.println("2. Show Recently Sent Messages");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");

                String menuInput = scanner.nextLine().trim();

                try {
                    choice = Integer.parseInt(menuInput);
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Invalid option. Please choose 1, 2 or 3.");
                    choice = 0;
                    continue;
                }

                switch (choice) {
                    case 1:
                        SentMessage(scanner);
                        break;
                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                    case 3:
                        System.out.println("Exiting Messaging Menu.");
                        break;
                    default:
                        System.out.println(
                                "Invalid option. Please choose 1, 2 or 3.");
                        break;
                }
            } while (choice != 3);

        } else {
            System.out.println(registrationMessage);
        }

        scanner.close();
    }

    /**
     * Processes the requested number of messages.
     *
     * @param scanner scanner used for input
     * @return an empty string after processing, or an error message
     */
    public static String SentMessage(Scanner scanner) {
        System.out.print("How many messages would you like to enter? ");

        int numberOfMessages;

        try {
            numberOfMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return "Please enter a valid number.";
        }

        if (numberOfMessages <= 0) {
            System.out.println("Please enter at least one message.");
            return "Please enter at least one message.";
        }

        for (int i = 0; i < numberOfMessages; i++) {
            System.out.println("\n=== Message " + (i + 1) + " ===");
            Message currentMessage = new Message();

            while (true) {
                System.out.print(
                        "Enter recipient cellphone (+27XXXXXXXXX): ");
                currentMessage.setRecipient(scanner.nextLine().trim());

                if (currentMessage.checkRecipientCell().equals(
                        "Cell phone number successfully captured.")) {
                    break;
                }

                System.out.println(
                        "Cell phone number is incorrectly formatted or "
                        + "does not contain an international code. "
                        + "Please correct the number and try again.");
            }

            while (true) {
                System.out.print(
                        "Enter your message (maximum 250 characters): ");
                currentMessage.setMessage(scanner.nextLine());

                if (currentMessage.checkMessageLength()
                        && !currentMessage.getMessage().trim().isEmpty()) {
                    System.out.println(
                            currentMessage.getMessageLengthFeedback());
                    break;
                }

                if (currentMessage.getMessage() != null
                        && currentMessage.getMessage().length() > 250) {
                    System.out.println(
                            currentMessage.getMessageLengthFeedback());
                } else {
                    System.out.println(
                            "Please enter a valid, non-empty message.");
                }
            }

            currentMessage.setMessageNumber(i);
            String hash = currentMessage.createMessageHash();
            currentMessage.setMessageHash(hash);

            String action;

            while (true) {
                System.out.println("1. Send Message");
                System.out.println("2. Disregard Message");
                System.out.println("3. Store Message to send later");
                System.out.print("Choose an action: ");

                action = scanner.nextLine().trim();

                if (action.equals("1") || action.equals("2")
                        || action.equals("3")) {
                    break;
                }

                System.out.println(
                        "Invalid choice. Please choose 1, 2 or 3.");
            }

            switch (action) {
                case "1":
                    currentMessage.setMessageStatus("Sent");
                    sentMessages.add(currentMessage);
                    allMessages.add(currentMessage);

                    System.out.println("Message sent");
                    System.out.println(
                            "Message ID: " + currentMessage.getMessageID());
                    System.out.println("Message Hash: " + hash);
                    System.out.println(
                            "Recipient: " + currentMessage.getRecipient());
                    System.out.println(
                            "Message: " + currentMessage.getMessage());
                    break;

                case "2":
                    System.out.println("Press 0 to delete the message.");
                    String deleteChoice = scanner.nextLine().trim();

                    if (deleteChoice.equals("0")) {
                        currentMessage.setMessageStatus("Disregarded");
                        allMessages.add(currentMessage);
                        System.out.println(
                                "Message successfully disregarded.");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;

                case "3":
                    String storageResult = currentMessage.storeMessage();
                    System.out.println(storageResult);

                    if (storageResult.equals(
                            "Message successfully stored.")) {
                        allMessages.add(currentMessage);
                    }
                    break;

                default:
                    break;
            }
        }

        return "";
    }
}
