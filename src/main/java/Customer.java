import java.util.Scanner;

public class Customer {
    MYSQL mysql = new MYSQL();
    private String customerName, customerAdress;
    private int customerPhone, customerNumber, money;
    private Scanner in = new Scanner(System.in);

    public Customer(String customerName, String customerAdress, int customerPhone, int customerNumber, int money) {
        this.customerName = customerName;
        this.customerAdress = customerAdress;
        this.customerPhone = customerPhone;
        this.customerNumber = customerNumber;

        //Penge er i øre
        this.money = money;
    }

    public void customerInterface() {
        int userInput;
        //Konversion fra øre til hele kroner
        double moneyConverted = (float) money / 100.00;
        System.out.printf("Velkommen " + customerName + ", din bank balance er: %.2f DKK", moneyConverted);

        System.out.println("Menu:\n" +
                "1. View account information\n" +
                "2. QUIT");

        System.out.print("Indtast menu nummer: ");
        userInput = in.nextInt();

        switch (userInput) {
            case 1:
                showUserInfo();
                break;
            default:
                break;
        }
    }

    private void showUserInfo() {

    }
}
