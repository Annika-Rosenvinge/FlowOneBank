import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private final MYSQL mysql;
    private final String customerName, customerAdress;
    private final int customerPhone, customerNumber;
    private int balance;
    private final Scanner in = new Scanner(System.in);
    private int userInput;
    private double balanceConverted;

    public Customer(String customerName, String customerAdress, int customerPhone, int customerNumber, int balance, MYSQL mysql) {
        this.customerName = customerName;
        this.customerAdress = customerAdress;
        this.customerPhone = customerPhone;
        this.customerNumber = customerNumber;
        this.mysql = mysql;

        //Penge er i øre/int (For at forhindre potentialle rounding errors)
        this.balance = balance;
        this.balanceConverted = (float) balance / 100.00;
    }

    // Hovedemenu for kunder
    public void customerInterface() {
        System.out.printf("Velkommen " + customerName + ", din bank balance er: %.2f DKK\n\n", balanceConverted);

        textMenuKey(true, "Menu:\n" +
                "1. Se kunde information\n" +
                "2. Se transaktionshistorik\n" +
                "3. Withdraw/Deposit\n" +
                "4. QUIT\n\n" +
                "INPUT: ");

        switch (userInput) {
            case 1:
                showUserInfo();
                customerInterface();
                break;
            case 2:
                showTransactionInfo();
                customerInterface();
                break;
            case 3:
                withdrawDepositInterface();
                customerInterface();
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    // Viser kunde information
    private void showUserInfo() {
        textMenuKey(true, "Kunde information:\n" +
                "Navn: " + customerName + "\n" +
                "Telefon: " + customerPhone + "\n" +
                "Adresse: " + customerAdress + "\n" +
                "Balance: " + balanceConverted + " DKK\n\n" +
                "Tast 1 for at forlade kunde information: ");
    }

    // Viser transaktionshistorik
    private void showTransactionInfo() {
        ArrayList<String[]> transactions = mysql.getTransactionHistory(customerNumber);
        System.out.println("Transaktionshistorik:");

        for (String[] transaction : transactions) {
            System.out.println(transaction[0] + " - " + transaction[1] + " - " + transaction[2]);
        }

        textMenuKey(true, "Tast 1 for at forlade kunde information: ");
    }

    // Withdraw/deposit menu
    private void withdrawDepositInterface() {
        System.out.printf("Din nuværende balance er: %.2f DKK:\n" +
                "1. Hæv penge\n" +
                "2. Indsæt penge\n" +
                "3. Tilbage til menuen\n\n" +
                "Indtast menu nummer: ", balanceConverted);

        textMenuKey(false, "");

        switch (userInput) {
            case 1:
                withdraw();
                break;
            case 2:
                deposit();
                break;
            default:
                break;
        }
    }

    //Withdraw penge til konto
    private void withdraw() {
        userInput = 0;
        while (userInput <= 0) {
            System.out.printf("Din nuværende balance er: %.2f DKK\n" +
                    "Indtast ønsket beløb at hæve: ", balanceConverted);

            textMenuKey(false, "");

            if (userInput <= 0) {
                System.out.println("Indtast et gyldigt beløb");
            }
        }

        if ((userInput * 100) <= balance) {
            moneyController(-userInput);
            mysql.transactionUpdate(customerNumber, customerName, balance, userInput, "Withdraw");
        } else {
            System.out.println("Utilstrækkelig balance på kontien");
        }
    }

    // Deposit penge til konto
    private void deposit() {
        userInput = 0;
        while (userInput <= 0) {
            System.out.printf("Din nuværende balance er: %.2f DKK\n" +
                    "Indtast ønsket beløb at indsætte: ", balanceConverted);

            textMenuKey(false, "");

            if (userInput <= 0) {
                System.out.println("Indtast et gyldigt beløb");
            }
        }

        moneyController(userInput);
        mysql.transactionUpdate(customerNumber, customerName, balance, userInput, "Deposited");
    }

    // Metode til at håndtere prints og inputs
    private void textMenuKey(boolean needPrint, String print) {
        if (needPrint) {
            System.out.print(print);
        }

        userInput = in.nextInt();
        in.nextLine();
        System.out.println();
    }

    // Ændre balancen i mysql databasen (balance bliver automatisk opdateret, fordi vi kalder updateMoney())
    private void moneyController(int moneyChange) {
        updateMoney();
        int moneyToUpdate = balance + moneyChange * 100;
        mysql.updateMoney(customerNumber, moneyToUpdate);
        updateMoney();
    }

    // Henter balance fra mysql databasen
    private void updateMoney() {
        balance = mysql.getMoney(customerNumber);
        balanceConverted = (float) balance / 100.00;
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int getCustomerBalance() {
        return balance;
    }
}