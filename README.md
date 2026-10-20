# PROG5121 — QuickChat

## Part 1: User Registration and Login

### Description

A Java console application that allows users to register an account and log in using their registered username and password.

### Features

- Username validation.
- Password complexity validation.
- South African cellphone number validation.
- User registration.
- User login authentication.
- Login status messages.

### Testing

JUnit tests cover username validation, password complexity, cellphone number validation, and login functionality.

---

## Part 2: Messaging System

### Description

Part 2 extends the original application by introducing a menu-driven messaging system that allows authenticated users to send, disregard, or store messages.

### Features

- Display a messaging menu after successful login.
- Allow users to choose how many messages to enter.
- Validate recipient cellphone numbers.
- Validate message length, with a maximum of 250 characters.
- Generate 10-digit message IDs.
- Generate message hashes using the message ID, message number, and message text.
- Send messages and display confirmation messages.
- Disregard messages.
- Store messages in JSON format.
- Display the total number of sent messages.
- Provide an option for recently sent messages.

### Testing

JUnit tests cover message ID generation, recipient validation, message length, message hash generation, message actions, and JSON storage.

## Technologies Used

- Java
- Maven
- JUnit 5
- JSON file storage
- Apache NetBeans

## Running the Application

Open the project in Apache NetBeans and run the `PROG5121_Part1` main class.

To execute the automated tests from the project directory, run:

```bash
mvn clean test
```

## References

Independent Communications Authority of South Africa (ICASA) (2016) *Numbering Plan Regulations*. Government Gazette No. 39861, Notice No. 370, 24 March. Available at: https://www.icasa.org.za/legislation-and-regulations/numbering-plan-regulations (Accessed: 20 October 2026).

Oracle (2026) *Java SE 26: Files Class*. Available at: https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/nio/file/Files.html (Accessed: 20 October 2026).