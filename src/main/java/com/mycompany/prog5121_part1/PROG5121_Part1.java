/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 */

package com.mycompany.prog5121_part1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Dineo Lesabe
 */
public class PROG5121_Part1 {

    private static int messageCount = 0;
    private static final ArrayList<Message> sentMessages = new ArrayList<>();

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No sent messages yet.";
        }

        StringBuilder output = new StringBuilder();

        for (Message sentMessage : sentMessages) {
            output.append("Message ID: ")
                    .append(sentMessage.getMessageID())
                    .append(System.lineSeparator())
                    .append("Message Hash: ")
                    .append(sentMessage.getMessageHash())
                    .append(System.lineSeparator())
                    .append("Recipient: ")
                    .append(sentMessage.getRecipient())
                    .append(System.lineSeparator())
                    .append("Message: ")
                    .append(sentMessage.getMessage())
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
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

            System.out.println();
            System.out.println("=== Login ===");

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
                System.out.println();
                System.out.println("=== Messaging Menu ===");
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

    public static String SentMessage(Scanner scanner) {

        System.out.print("How many messages would you like to enter? ");
        int numberOfMessages;

        try {
            numberOfMessages = Integer.parseInt(
                    scanner.nextLine().trim());
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

                currentMessage.setRecipient(
                        scanner.nextLine().trim());

                if (currentMessage.checkRecipientCell().equals(
                        "Cell phone number successfully captured.")) {
                    break;
                }

                System.out.println(
                        "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            }

            while (true) {
                System.out.print(
                        "Enter your message (maximum 250 characters): ");

                currentMessage.setMessage(scanner.nextLine());

                if (currentMessage.checkMessageLength()
                        && !currentMessage.getMessage().trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Please enter a valid message of no more than 250 characters.");
            }

            currentMessage.setMessageNumber(messageCount);

            String hash = currentMessage.createMessageHash();
            currentMessage.setMessageHash(hash);

            messageCount++;

            String action;

            while (true) {
                System.out.println("1. Send Message");
                System.out.println("2. Disregard Message");
                System.out.println("3. Store Message to send later");
                System.out.print("Choose an action: ");

                action = scanner.nextLine().trim();

                if (action.equals("1")
                        || action.equals("2")
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

                    System.out.println("Message successfully sent");
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
                        System.out.println(
                                "Message successfully disregarded.");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;

                case "3":
                    System.out.println(currentMessage.storeMessage());
                    break;

                default:
                    break;
            }
        }

        return "";
    }
}
