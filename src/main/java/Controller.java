import java.util.Scanner;

public class Controller {
    Scanner sc = new Scanner (System.in);



    public void runInitProgram(){
        System.out.println("Er du kunde eller medarbejder? K/M ");
        String choice;
        choice = sc.next("K");
        if (choice == "K"){
            //Lav en metode i customer der skal køres her, så man som kunde kan gøre de ting der står i user stories
        }

        else{
            //medarbejder ^
        }
    }


}

