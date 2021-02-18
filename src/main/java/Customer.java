import java.sql.SQLOutput;
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
                "2. Se transaktionshistorik\n" +
                "3. Withdraw/Deposit\n" +
                "4. QUIT");

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
            case 3:
                showMoneyChange();
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

    private void showTransactionInfo() {
        ArrayList<String []> transactions = mysql.getTransactionHistory(customerNumber);
        System.out.println("transaktionshistorik:");

        for(int i = 0; i < transactions.size(); i++) {
            String [] transaction = transactions.get(i);
            System.out.println(transaction[0] + " - " + transaction [1] + " - " + transaction[2]);
        }

        System.out.print("Tast 1 for at forlade kunde information: ");
        userInput = in.nextInt();
        in.nextLine();

        customerInterface();
    }

    private void showMoneyChange() {
        double moneyConverted = (float) money / 100.00;
        System.out.printf("Din nuværende balance er: %.2f DKK:\n" +
                "1. Hæv penge\n" +
                "2. Indsæt penge\n" +
                "3. Tilbage til menuen\n\n" +
                "Indtast menu nummer: ", moneyConverted);

        userInput = in.nextInt();
        in.nextLine();

        System.out.println("\n");

        switch (userInput) {
            case 1:
                userInput = 0;
                while(userInput <= 0) {
                    System.out.printf("Din nuværende balance er: %.2f DKK\n" +
                            "Indtast ønsket beløb at hæve: ", moneyConverted);

                    userInput = in.nextInt();
                    in.nextLine();

                    System.out.println("\n");

                    if(userInput <= 0) {
                        System.out.println("Indtast et gyldigt beløb");
                    }
                }

                if((userInput * 100) <= money ) {
                    moneyController(-userInput);
                    mysql.transactionUpdate(customerNumber,customerName,money,userInput, "Withdraw");
                } else {
                    System.out.println("Utilstrækkelig balance på kontien");
                }

                break;
            case 2:
                userInput = 0;
                while(userInput <= 0) {
                    System.out.printf("Din nuværende balance er: %.2f DKK\n" +
                            "Indtast ønsket beløb at indsætte: ", moneyConverted);

                    userInput = in.nextInt();
                    in.nextLine();

                    System.out.println("\n");

                    if(userInput <= 0) {
                        System.out.println("Indtast et gyldigt beløb");
                    }
                }

                moneyController(userInput);
                mysql.transactionUpdate(customerNumber,customerName,money,userInput, "Deposited");
                break;
            default:
                break;
        }
        customerInterface();
    }

    private void moneyController(int moneyChange) {
        updateMoney();
        int moneyToUpdate = money + moneyChange * 100;
        mysql.updateMoney(customerNumber, moneyToUpdate);
        updateMoney();
    }

    private void updateMoney() {
        money = mysql.getMoney(customerNumber);
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
