import java.util.Scanner;

public class Controller {
    Scanner in = new Scanner (System.in);
    int userInput;


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

        switch(userInput) {
            case 1:
                createUserInterface();
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                startCustomerUserInterface();
                break;
        }
    }

    private void createUserInterface() {
        String username, password = null;

        System.out.print("Vælg et brugernavn: ");
        username = in.nextLine();

        while(password == null) {
            password = createPassword();
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

