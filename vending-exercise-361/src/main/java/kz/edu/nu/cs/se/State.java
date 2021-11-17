package kz.edu.nu.cs.se;

/**
 * State abstraction for vending machine exercise
 */
public abstract class State {
    
    /**
     * Store reference to the vending machine 
     */
    protected VendingMachine vendingMachine;
    
    protected String name;

    public State (VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    
    public abstract void insertCoin(int coin);
    
    public abstract int refund();
    
    public abstract int vend();

    @Override
    public String toString() {
        return this.name;
    }
}
