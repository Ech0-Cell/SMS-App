package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Operator Web Selfcare");
            System.out.println("1. Create Subscriber");
            System.out.println("2. Show Remaining Balance List");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    aomClient.createSubscriber();
                    break;
                case 2:
                    aomClient.showRemainingBalanceList();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }

        }
    }
}