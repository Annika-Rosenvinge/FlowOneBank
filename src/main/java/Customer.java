public class Customer implements Login {
    public int balance;
    public int money;

    // sæt saldoen til 0,-
    public Customer() {
            balance = 0;
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
}
