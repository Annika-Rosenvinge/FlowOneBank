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
        System.out.println("Skriv valgene her under");
        while (choice != 5){
            choice = sc.nextInt();
            switch (choice){
                case 1: newCostumer(); break;
                case 2: removeCostumer(); break;
                case 3: customerChanges(); break;
                case 4: moveMoney(); break;

            }
        }
    }
    private void newCostumer(){
        //tilføj en ny kunde til SQL fil
        //kunde: navn, telefon nummer, adresse
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
}
