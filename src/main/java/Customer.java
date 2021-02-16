public class Customer implements Login {
    public int balance;
    int money;

    // sæt saldoen til 0,-
    public Customer() {
            balance = 0;
    }

    // indsæt penge
    public void indsætPenge (money) {
        balance = balance + money;
    }


    // Hæv Penge
    public int hævPenge(money) {
        if (balance - money >= 0) {
            balance = balance - money;
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
}
