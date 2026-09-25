
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121_part1;

/**
 *
 * @author Dineo Lesabe
 */
public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;

    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public boolean checkPasswordComplexity() {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // South African telephone numbering information based on ICASA's
    // National Numbering Plan Regulations.
    // Source: Independent Communications Authority of South Africa (ICASA).
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber != null
                && cellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Registration successful.";
    }
}
