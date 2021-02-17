import java.util.Scanner;

//passwords mangler

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
    public Customer(String name, int phoneNumber) {
        this.customerId = counter;
        this.name = name;
        this.phoneNumber = phoneNumber;
        counter++;

    }

    public void login() {

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

    private void ikkePengeNokPåKonti(){
        System.out.println("Du har desværre ikke penge nok på kontoen. Indsæt venligst penge og prøv igen.");
    }


    //import sql server


    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber (int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    private int getBalance(){
        return balance;
    }

}
