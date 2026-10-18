/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 */

package com.mycompany.prog5121_part1;

import java.util.Scanner;

/**
 *
 * @author Dineo Lesabe
 */
public class PROG5121_Part1 {
    private static int messageCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

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

            System.out.println();
            System.out.println("=== Messaging Menu ===");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
    case 1:
        sendMessages(scanner);
        break;

    case 2:
        System.out.println("Coming Soon.");
        break;

    case 3:
        System.out.println("Exiting Messaging Menu.");
        break;

    default:
        System.out.println("Invalid option. Please choose 1, 2 or 3.");
        break;
}
        } else {
            System.out.println(registrationMessage);
        }

              scanner.close();
    }

    private static void sendMessages(Scanner scanner) {

        System.out.print("How many messages would you like to enter? ");
        int numberOfMessages;

        try {
            numberOfMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        if (numberOfMessages <= 0) {
            System.out.println("Please enter at least one message.");
            return;
        }

        for (int i = 0; i < numberOfMessages; i++) {

            System.out.println("\n=== Message " + (i + 1) + " ===");

            Message currentMessage = new Message();

            while (true) {
                System.out.print("Enter recipient cellphone (+27XXXXXXXXX): ");
                currentMessage.setRecipient(scanner.nextLine().trim());

                if (currentMessage.checkRecipientCell()) {
                    break;
                }

                System.out.println(
                        "Invalid cellphone number. Use +27 followed by 9 digits.");
            }

            while (true) {
                System.out.print("Enter your message (maximum 250 characters): ");
                currentMessage.setMessage(scanner.nextLine());

                if (currentMessage.checkMessageLength()
                        && !currentMessage.getMessage().trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Message must not be empty or exceed 250 characters.");
            }

            messageCount++;
            currentMessage.setMessageNumber(messageCount);

            String hash = currentMessage.createMessageHash();
            currentMessage.setMessageHash(hash);

            System.out.println("Message ID: " + currentMessage.getMessageID());
            System.out.println("Message Hash: " + hash);

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

                System.out.println("Invalid choice. Please choose 1, 2 or 3.");
            }

            switch (action) {
                case "1":
                    currentMessage.setMessageStatus("Sent");
                    System.out.println("Message successfully sent.");
                    break;

                case "2":
                    currentMessage.setMessageStatus("Disregarded");
                    System.out.println("Press 0 to delete the message.");
                    break;

                case "3":
                    System.out.println(currentMessage.storeMessageToJson());
                    break;
            }
        }
    }
}