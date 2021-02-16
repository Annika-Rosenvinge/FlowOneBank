import java.util.Scanner;

public class Customer implements Login {
    Scanner scName = new Scanner(System.in);
    Scanner scNumber = new Scanner(System.in);

    static int counter = 1;
    public int balance;
    public int money;


    int customerId = 0;
    public String name;
    int phoneNumber;


    // creates customer
    public Customer(String name) {
        this.customerId = counter;
        this.name = name;
        int phoneNumber;
        balance = 0;
        counter++;

    }

    // indsæt penge
    public int indsætPenge (int money) {
        this.money = money;
        return balance = balance + money;
    }


    // Hæv Penge
    public void hævPenge(int money) {
        this.money = money;
        if (balance - money >= 0) {
            System.out.println(balance = balance - money);
        }
        else {
            ikkePengeNokPåKonti();
        }

    }

    private int getBalance(){
        return balance;
    }

    private void ikkePengeNokPåKonti(){
        System.out.println("Du har desværre ikke penge nok på kontoen. Indsæt venligst penge og prøv igen.");
    }


    //import sql server
    public void login() {

    }

    public void setName(String name) {
        this.name = name;
    }
}
