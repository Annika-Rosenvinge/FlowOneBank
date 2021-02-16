public class Customer implements Login {
    private int balance;

    // sæt saldoen til 0,-
    public Customer() {
            balance = 0;
    }

    // indsæt penge
    public void indsætPenge (int mængdePenge) { balance = balance + mængdePenge;
    }


    // Hæv Penge
    public int hævPenge(int mængdePenge) {
        if (balance - mængdePenge >= 0) {
            balance = balance - mængdePenge;
    }else {
            ikke_penge_nok_på_konti();
        }


        // Se saldo
        public int getBalance(){
            return balance;
        }

        public void ikke_penge_nok_på_konti(){
            System.err.println("Du har desværre ikke penge nok på kontoen. Indsæt venligst penge og prøv igen.");
        }

    }








    //import sql server
    public void loginCus() {

    }
}
