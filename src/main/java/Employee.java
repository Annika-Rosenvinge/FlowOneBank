import java.util.ArrayList;
import java.util.Scanner;

public class Employee {
    private final Scanner in = new Scanner(System.in);
    private final String employeeName;
    private int userInput;
    private final Controller controller = new Controller();
    private final MYSQL mysql;

    public Employee(String employeeName, MYSQL mysql) {
        this.employeeName = employeeName;
        this.mysql = mysql;
    }

    // Hovedemenu for employees
    public void employeeInterface() {
        textMenuKey("Velkommen " + employeeName + "!\n\n" +
                "Menu:\n" +
                "1. Opret konti\n" +
                "2. Fjern konti\n" +
                "3. Redigere konti\n" +
                "4. Flyt penge\n" +
                "5. Se alle kontier\n" +
                "6. QUIT\n\n" +
                "INPUT: ");

        if(userInput < 6 && userInput > 1) printAllCustomers();

        switch (userInput) {
            case 1:
                controller.createUserInterface();
                employeeInterface();
                break;
            case 2:
                removeCostumer();
                employeeInterface();
                break;
            case 3:
                customerChangeSettings();
                employeeInterface();
                break;
            case 4:
                transferMoney();
                employeeInterface();
                break;
            case 5:
                showUserTransactionHistory();
                employeeInterface();
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    // Fjerner kunder fra databasen
    private void removeCostumer() {
        textMenuKey("Indtast kontonummer at slette (SKRIV 0 FOR AT ANNULLERE): ");

        if (userInput != 0) {
            mysql.removeUser(userInput);
        }
    }

    // Ændre kundernes indstillinger
    private void customerChangeSettings() {
        ArrayList<Customer> customers = mysql.getAllCustomers();
        int userEdit, customerPhone = 0;
        String customerName = null, customerAdresse = null;

        System.out.print("Indtast kontonummer at redigere (SKRIV 0 FOR AT ANNULLERE): ");
        userEdit = in.nextInt();
        in.nextLine();

        textMenuKey("ÆNDRINGS MENU:\n" +
                "1. Navn\n" +
                "2. Telefonnummer\n" +
                "3. Adresse\n" +
                "4. QUIT\n" +
                "Indtast menu nummer: ");

        for (Customer customer : customers) {
            if (customer.getCustomerNumber() == userEdit) {
                customerPhone = customer.getCustomerPhone();
                customerAdresse = customer.getCustomerAdress();
                customerName = customer.getCustomerName();
            }
        }

        if (userEdit != 0 && userInput > 0 && userInput < 4) {
            switch (userInput) {
                case 1:
                    System.out.print("Indtast nyt navn: ");
                    customerName = in.next();
                    in.nextLine();
                    break;
                case 2:
                    System.out.print("Indtast nyt nummer: ");
                    customerPhone = in.nextInt();
                    in.nextLine();
                    break;
                case 3:
                    System.out.print("Indtast ny adresse: ");
                    customerAdresse = in.next();
                    in.nextLine();
                    break;
                default:
                    break;
            }
            mysql.updateUserAccount(userEdit, customerName, customerPhone, customerAdresse);
            System.out.println("Brugeren er opdateret!");
        }
    }

    // Overfører penge tværs over 2 kontoer
    private void transferMoney() {
        int user1, user2, transferAmount;

        System.out.print("Vælg konto 1: ");
        user1 = in.nextInt();
        in.nextLine();

        System.out.print("Vælg beløb at overføre: ");
        transferAmount = in.nextInt();
        in.nextLine();

        System.out.print("Vælg konto 2: ");
        user2 = in.nextInt();
        in.nextLine();

        if (user1 != 0 && user2 != 0 && transferAmount != 0) {
            int user1Money = mysql.getMoney(user1);
            int user2Money = mysql.getMoney(user2);
            int convertedMoney = transferAmount * 100;

            if (user1Money > convertedMoney) {
                mysql.updateMoney(user1, user1Money - convertedMoney);
                mysql.updateMoney(user2, user2Money + convertedMoney);
            } else {
                System.out.println("Konto 1 har ikke tilstrækkelig balance");
            }
        } else {
            System.out.println("Ugyldigt input");
        }
    }

    // Printer alle kundernes kundeinformation (minus transaktionshistorik)
    private void printAllCustomers() {
        ArrayList<Customer> customers = mysql.getAllCustomers();

        for (Customer customer : customers) {
            System.out.printf(customer.getCustomerNumber() + ". " + customer.getCustomerName() + " (" + customer.getCustomerPhone() + ", " + customer.getCustomerAdress() + ") Saldo: %.2f DKK\n", (float) customer.getCustomerBalance() / 100.00);
        }

        System.out.println("\n");
    }

    // Viser kunders transaktionshistorik
    private void showUserTransactionHistory() {
        textMenuKey("Indtast et kontonummer for at se transaktionshistorik (Skriv 0 for at quit): ");

        if (userInput != 0) {
            ArrayList<String[]> transactions = mysql.getTransactionHistory(userInput);
            for (String[] transaction : transactions) {
                System.out.println(transaction[0] + " - " + transaction[1] + " - " + transaction[2]);
            }
            System.out.println("\n");
        } else {
            System.out.println("Ugyldigt input");
        }
    }

    // Metode til at håndtere prints og inputs
    private void textMenuKey(String print) {
        System.out.print(print);
        userInput = in.nextInt();
        in.nextLine();
        System.out.println();
    }
}