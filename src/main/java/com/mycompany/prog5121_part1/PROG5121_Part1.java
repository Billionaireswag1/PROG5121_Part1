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
        System.out.println("Send Messages selected.");
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
}