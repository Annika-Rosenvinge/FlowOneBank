import java.util.Scanner;

public class Employee implements Login {
    Scanner sc = new Scanner(System.in);

    public void login() {
        //opret medarbejdere i SQL og derefter skab forbindelse mellem
        //employee class og SQL, derefter oprettes customer, men tabellen for dem i SQL skal være oprettet
        //på forhånd
    }

    public void employee(){
        System.out.println("Velkommen til Ebberød Bank");
        System.out.println("Vælg fra menuen");

    }

    public void employeeMenu(){
        int choice = 0;
        System.out.println("Vælg;");
        while (choice != 5){
            choice = sc.nextInt();
            switch (choice){
                case 1: Customer customer = newCostumer(); commitCustomer(); break;
                case 2: removeCostumer(); break;
                case 3: customerChanges(); break;
                case 4: moveMoney(); break;

            }
        }
    }
    private Customer newCostumer(){
        String name = " ";
        int phoneNumber = 0;
        int balance = 0;
        Customer customer = new Customer(name);
        System.out.println("Indtast kundens navn: ");
        name = sc.next();
        customer.setName();
        phoneNumber = sc.nextInt();



        return customer;
    }

    private void removeCostumer(){
        //Fjern en kunde
        //brug kunde nummer og eller navn
    }

    private void  customerChanges(){
        //indtast navn og nummer på kunde
        //indsæt, hæv
    }

    private void moveMoney(){

    }

    private void commitCustomer(){
        //kunden der oprettes lægges op i serveren
    }
}
