import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    MYSQL mysql = new MYSQL();
    private String customerName, customerAdress;
    private int customerPhone, customerNumber, money;
    private Scanner in = new Scanner(System.in);
    private int userInput;

    public Customer(String customerName, String customerAdress, int customerPhone, int customerNumber, int money) {
        this.customerName = customerName;
        this.customerAdress = customerAdress;
        this.customerPhone = customerPhone;
        this.customerNumber = customerNumber;

        //Penge er i øre
        this.money = money;
    }

    public void customerInterface() {
        //Konversion fra øre til hele kroner
        double moneyConverted = (float) money / 100.00;
        System.out.printf("Velkommen " + customerName + ", din bank balance er: %.2f DKK\n\n", moneyConverted);

        System.out.println("Menu:\n" +
                "1. View account information\n" +
                "2. Se transaktionshistorik" +
                "3. QUIT");

        System.out.print("Indtast menu nummer: ");
        userInput = in.nextInt();
        in.nextLine();

        System.out.println("\n");

        switch (userInput) {
            case 1:
                showUserInfo();
                break;
            case 2:
                showTransactionInfo();
                break;
            default:
                break;
        }
    }

    private void showUserInfo() {
        double moneyConverted = (float) money / 100.00;
        System.out.print("Kunde information:\n" +
                "Navn: " + customerName + "\n" +
                "Telefon: " + customerPhone + "\n" +
                "Adresse: " + customerAdress + "\n" +
                "Balance: " + moneyConverted + " DKK\n\n" +
                "Tast 1 for at forlade kunde information: ");

        userInput = in.nextInt();
        in.nextLine();

        customerInterface();
    }

    // IKKE TESTET ENDNU
    private void showTransactionInfo() {
        ArrayList<String> transactions = mysql.getTransactionHistory(customerNumber);
        System.out.println("transaktionshistorik:");

        for(int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }

        System.out.print("Tast 1 for at forlade kunde information: ");
        userInput = in.nextInt();
        in.nextLine();

        customerInterface();
    }

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

    public int getMoney() {
        return money;
    }
}
