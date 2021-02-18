import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee {
    Scanner in = new Scanner(System.in);
    List<Customer> customers = new ArrayList<>();
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

                break;
            case 4:
                break;
            case 5:
                printAllCustomers();
                selectCustomer();
                break;
            default:
                break;
        }
        if (userInput > 0 && userInput <6) {
            employeeInterface();
        }
    }

    private void removeCostumer() {
        System.out.print("Indtast kontonummer at slette (SKRIV 0 FOR AT ANNULLERE): ");
        userInput = in.nextInt();
        in.nextLine();

        if(userInput != 0) {
            mysql.removeUser(userInput);
        }

        userInput = 1;
    }


    private void customerChanges() {
        //indtast navn og nummer på kunde
        //indsæt, hæv
    }

    private void moveMoney() {

    }

    private void printAllCustomers() {
        ArrayList<Customer> customers = mysql.getAllCustomers();

        for(Customer customer: customers) {
            System.out.println(customer.getCustomerNumber() + ". " + customer.getCustomerName() + " (" + customer.getCustomerPhone() + ", " + customer.getCustomerAdress() + ") Saldo: " + customer.getMoney() + " DKK");
        }

        System.out.println("\n");
    }

    private void selectCustomer() {
        System.out.print("Indtast et kontonummer for at se transaktionshistorik (Skriv 0 for at quit): ");
        userInput = in.nextInt();
        in.nextLine();

        if(userInput != 0) {
            ArrayList<String []> transactions= mysql.getTransactionHistory(userInput);
        }

        userInput = 1;
    }
}
