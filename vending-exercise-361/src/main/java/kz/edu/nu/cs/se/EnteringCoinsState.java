package kz.edu.nu.cs.se;

public final class EnteringCoinsState extends State {

    public EnteringCoinsState (VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void insertCoin(int coin) {
        if (coin != 50 && coin != 100) {
            throw new IllegalArgumentException();
        }
        vendingMachine.balance += coin;
        if (vendingMachine.balance >= 200) {
            vendingMachine.setCurrentState(vendingMachine.paid);
        }
    }

    @Override
    public int refund() {
        vendingMachine.balance = 0;
        vendingMachine.setCurrentState(vendingMachine.idle);
        return 0;
    }

    @Override
    public int vend() {
        throw new IllegalStateException();
    }
}
