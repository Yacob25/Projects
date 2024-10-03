package org.example.BankingApplication;
import java.util.Scanner;

public class BankAccount {

    int balance;
    int previousTransaction;
    String customerName;
    String customerId;

    public BankAccount(String cName, String cId){
        customerName = cName;
        customerId = cId;
    }

    void deposit(int amount){
        if (amount != 0){
            balance = balance + amount;
            previousTransaction = amount;
        }
    }

    void withdraw(int amount){
        if (amount != 0){
            balance = balance - amount;
            previousTransaction = -amount;
        }
    }

    void get(int amount){
        if (amount != 0){
            balance = balance - amount;
            previousTransaction = -amount;
        }
    }

    void getPreviousTransaction(){
        if (previousTransaction > 0){
            System.out.println("Deposited: " + previousTransaction);
        }
        else if (previousTransaction < 0){
            System.out.println("Withdraw " + Math.abs(previousTransaction));
        }
        else {
            System.out.println("No transaction occurred ");
        }


    }

    void showMenu(){

        char option = '\0';
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + customerName);
        System.out.println("Your ID is " +customerId);
        System.out.println("\n");
        System.out.println("A. Check Balance  " );
        System.out.println("B. Deposit  ");
        System.out.println("C. Withdraw  ");
        System.out.println("D. Previous Transaction " );
        System.out.println("E. Exit " );
        do
        {
            System.out.println("======================================================================");
            System.out.println("Enter an Option ");
            System.out.println("========================================================================");
            option = scanner.next().charAt(0);
            System.out.println("\n");
            switch (option) {
                case 'A':
                    System.out.println("------------------------");
                    System.out.println("Balance= " + balance);
                    System.out.println("------------------------");
                    System.out.println("\n");
                    break;

                case 'B':
                    System.out.println("------------------------");
                    System.out.println("Enter an amount to deposit: ");
                    System.out.println("------------------------");
                    int amount = scanner.nextInt();
                    deposit(amount);
                    System.out.println("\n");
                    break;

                case 'C':
                    System.out.println("------------------------");
                    System.out.println("Enter an amount to deposit withdraw");
                    System.out.println("------------------------");
                    int withdrawAmt = scanner.nextInt();
                    deposit(withdrawAmt);
                    System.out.println("\n");
                    break;


                case 'D':
                    System.out.println("------------------------");
                    getPreviousTransaction();
                    System.out.println("------------------------");
                    System.out.println("\n");
                    break;

                case 'E':
                    System.out.println("***********************************");
                    break;

                default:
                    System.out.println("Invalid option! Please enter characters A-E. ");
                    break;
            }
        }  while (option != 'E');

        System.out.println("Thank you and have a great day.");
    }


}
