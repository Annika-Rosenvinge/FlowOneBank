import java.util.Scanner;

public class Controller {
    Scanner sc = new Scanner (System.in);

    public void runProgram(){
        int choice = 0;
        cusOrEmp();
    }

    private void cusOrEmp(){
        System.out.println("Er du kunde (1) eller medarbejder? (2) ");
        int choice;
        choice = sc.nextInt();
        if (choice == 1){
            //kunde
        }

        else{
            //medarbejder
        }
    }

}

