import java.util.Scanner;

public class Controller {
    private Scanner in = new Scanner (System.in);
    MYSQL mysql = new MYSQL();
    private int userInput;
    Customer customerBank;

    public void runInitProgram(){
        System.out.print("Velkommen til Ebberød Bank!\nTast 1 for kunde/Tast 2 for medarbejder: ");
        userInput = in.nextInt();

        switch(userInput) {
            case 1:
                startCustomerUserInterface();
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                runInitProgram();
                break;
        }
    }

    private void startCustomerUserInterface() {
        System.out.print("Tast 1 for logge ind/Tast 2 for at oprette en bruger: ");
        userInput = in.nextInt();
        in.nextLine();
        switch(userInput) {
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

    private void userLoginInterface() {
        String username, password;

        System.out.print("Indtast brugernavn: ");
        username = in.next();

        System.out.print("Indtast password: ");
        password = in.next();

        if(mysql.userLoginCheck(username, password)) {
            customerBank = mysql.loadUser(username);
            customerBank.customerInterface();
        } else {
            System.out.println("Ugyldigt brugernavn eller login");
            runInitProgram();
        }
    }

    private void createUserInterface() {
        String username, password = null, name, adresse, phone;

        System.out.print("Indtast dit fuldenavn: ");
        name = in.nextLine();

        System.out.print("Indtast din adresse: ");
        adresse = in.nextLine();

        System.out.print("Indtast dit telefon nummer: ");
        phone = in.nextLine();

        System.out.print("Vælg et brugernavn: ");
        username = in.nextLine();

        while(password == null) {
            password = createPassword();
        }

        if(mysql.createUserMYSQL(username, password, name, adresse, phone)) {
            System.out.println("Kontoen er oprettet");
        } else {
            System.out.println("FEJL (MYSQL)");
        }

    }

    private String createPassword() {
        String code1, code2;
        System.out.print("Vælg en kode: ");
        code1 = in.nextLine();
        System.out.print("Gentag koden: ");
        code2 = in.nextLine();

        if(code1.equals(code2)) {
            return code1;
        } else {
            return null;
        }
    }

}

