import java.util.ArrayList;
import java.util.Scanner;

public class Employee {
    Scanner in = new Scanner(System.in);
    String employeeName;
    int userInput;
    Controller controller = new Controller();
    MYSQL mysql = new MYSQL();

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public void employeeInterface() {
        System.out.println("Velkommen " + employeeName + "!");

        System.out.println("Menu:\n" +
                "1. Opret konti\n" +
                "2. Fjern konti\n" +
                "3. Redigere konti\n" +
                "4. Flyt penge\n" +
                "5. Se alle kontier\n" +
                "6. QUIT");

        System.out.print("Indtast menu nummer: ");
        userInput = in.nextInt();
        in.nextLine();

        System.out.println("\n");

        switch (userInput) {
            case 1:
                controller.createUserInterface();
                break;
            case 2:
                printAllCustomers();
                removeCostumer();
                break;
            case 3:
                printAllCustomers();
                customerChanges();
                break;
            case 4:
                printAllCustomers();
                moveMoney();
                break;
            case 5:
                printAllCustomers();
                selectCustomer();
                break;
            default:
                break;
        }

        if (userInput > 0 && userInput < 6) {
            employeeInterface();
        }
    }

    private void removeCostumer() {
        System.out.print("Indtast kontonummer at slette (SKRIV 0 FOR AT ANNULLERE): ");
        userInput = in.nextInt();
        in.nextLine();

        if (userInput != 0) {
            mysql.removeUser(userInput);
        }

        userInput = 1;
    }

    private void customerChanges() {
        ArrayList<Customer> customers = mysql.getAllCustomers();
        int userEdit, customerPhone = 0;
        String customerName = null, customerAdresse = null;

        System.out.print("Indtast kontonummer at redigere (SKRIV 0 FOR AT ANNULLERE): ");
        userEdit = in.nextInt();
        in.nextLine();

        System.out.print("ÆNDRINGS MENU:\n" +
                "1. Navn\n" +
                "2. Telefonnummer\n" +
                "3. Adresse\n" +
                "4. QUIT\n" +
                "Indtast menu nummer: ");

        userInput = in.nextInt();
        in.nextLine();

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
            System.out.println("Brugeren er ny opdateret!");
        }

        userInput = 1;
    }

    private void moveMoney() {
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

        userInput = 1;
    }

    private void printAllCustomers() {
        ArrayList<Customer> customers = mysql.getAllCustomers();

        for (Customer customer : customers) {
            System.out.printf(customer.getCustomerNumber() + ". " + customer.getCustomerName() + " (" + customer.getCustomerPhone() + ", " + customer.getCustomerAdress() + ") Saldo: %.2f DKK\n", (float) customer.getMoney() / 100.00);
        }

        System.out.println("\n");
    }

    private void selectCustomer() {
        System.out.print("Indtast et kontonummer for at se transaktionshistorik (Skriv 0 for at quit): ");
        userInput = in.nextInt();
        in.nextLine();

        if (userInput != 0) {
            ArrayList<String[]> transactions = mysql.getTransactionHistory(userInput);
            for (String[] transaction : transactions) {
                System.out.println(transaction[0] + " - " + transaction[1] + " - " + transaction[2]);
            }
            System.out.println("\n");
        } else {
            System.out.println("Ugyldigt input");
        }

        userInput = 1;
    }
}
