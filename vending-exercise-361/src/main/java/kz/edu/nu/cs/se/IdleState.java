package kz.edu.nu.cs.se;

public final class IdleState extends State {

    public IdleState (VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void insertCoin(int coin) {
        if (coin != 50 && coin != 100) {
            throw new IllegalArgumentException();
        }
        vendingMachine.balance += coin;
        vendingMachine.setCurrentState(vendingMachine.enteringCoins);
    }

    @Override
    public int refund() {
        return 0;
    }

    @Override
    public int vend() {
        throw new IllegalStateException();
    }
}
