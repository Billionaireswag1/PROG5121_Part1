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

        } else {
            System.out.println(registrationMessage);
        }

        scanner.close();
    }
}