import java.util.Scanner;

public class Controller {
    private final Scanner in = new Scanner(System.in);
    private MYSQL mysql = new MYSQL();
    private int userInput;
    Customer customerBank;

    // Start menu (Første interface)
    public void runInitProgram() {
        textMenuKey("Velkommen til Ebberød Bank!\nTast 1 for kunde/Tast 2 for medarbejder: ");

        switch (userInput) {
            case 1:
                startCustomerUserInterface();
                break;
            case 2:
                startEmployeeUserInterface();
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                runInitProgram();
                break;
        }
    }

    // Kunde interface
    private void startCustomerUserInterface() {
        textMenuKey("Tast 1 for logge ind/Tast 2 for at oprette en bruger: ");

        switch (userInput) {
            case 1:
                userLoginInterface();
                break;
            case 2:
                createUserInterface();
                startCustomerUserInterface();
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                startCustomerUserInterface();
                break;
        }
    }

    // Employee interface
    private void startEmployeeUserInterface() {
        textMenuKey("Tast 1 for logge ind/Tast 2 for at oprette en employee bruger: ");

        switch (userInput) {
            case 1:
                employeeLoginInterface();
                break;
            case 2:
                createEmployeeInterface();
                startEmployeeUserInterface();
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                startEmployeeUserInterface();
                break;
        }
    }

    // Employee login interface
    private void employeeLoginInterface() {
        String username, password;

        System.out.print("Indtast brugernavn: ");
        username = in.next();

        System.out.print("Indtast password: ");
        password = in.next();

        if (mysql.employeeLoginCheck(username, password)) {
            Employee employee = new Employee(username, mysql);
            employee.employeeInterface();
        } else {
            System.out.println("Ugyldigt brugernavn eller login");
            runInitProgram();
        }
    }

    // Employee account oprettelse
    private void createEmployeeInterface() {
        String username, password = null;

        System.out.print("Vælg et brugernavn: ");
        username = in.nextLine();

        while (password == null) {
            password = createPassword();
        }

        if (mysql.createEmployeeMYSQL(username, password)) {
            System.out.println("Kontoen er oprettet\n-----------------\n\n");
        } else {
            System.out.println("FEJL (MYSQL)");
        }
    }

    // Kunde login interface
    private void userLoginInterface() {
        String username, password;

        System.out.print("Indtast brugernavn: ");
        username = in.next();

        System.out.print("Indtast password: ");
        password = in.next();

        if (mysql.userLoginCheck(username, password)) {
            customerBank = mysql.loadUser(username, mysql);
            System.out.println("\n-----------------\n\n");
            customerBank.customerInterface();
        } else {
            System.out.println("Ugyldigt brugernavn eller login");
            runInitProgram();
        }
    }

    // Kunde account oprettelse
    public void createUserInterface() {
        String username, password = null, name, adresse, phone;

        System.out.print("Indtast dit fuldenavn: ");
        name = in.nextLine();

        System.out.print("Indtast din adresse: ");
        adresse = in.nextLine();

        System.out.print("Indtast dit telefon nummer: ");
        phone = in.nextLine();

        System.out.print("Vælg et brugernavn: ");
        username = in.nextLine();

        while (password == null) {
            password = createPassword();
        }

        if (mysql.createUserMYSQL(username, password, name, adresse, phone)) {
            System.out.println("Kontoen er oprettet\n-----------------\n\n");
        } else {
            System.out.println("FEJL (MYSQL)");
        }
    }

    // Metode til at oprette koder
    private String createPassword() {
        String code1, code2;
        System.out.print("Vælg en kode: ");
        code1 = in.nextLine();
        System.out.print("Gentag koden: ");
        code2 = in.nextLine();

        if (code1.equals(code2)) {
            return code1;
        } else {
            return null;
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