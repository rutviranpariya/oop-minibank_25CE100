public class MiniProject8 {
    static class InsufficientFundsException extends Exception {
        private final long shortfall;
        InsufficientFundsException(long shortfall){super("Insufficient funds: short by Rs."+shortfall);this.shortfall=shortfall;}
        public long getShortfall(){return shortfall;}
    }
    static class InvalidAmountException extends RuntimeException { InvalidAmountException(String message){super(message);} }
    static class Account {
        private long balance;
        Account(long balance){this.balance=balance;}
        void deposit(long amount){if(amount<=0)throw new InvalidAmountException("Amount must be positive");balance+=amount;}
        void withdraw(long amount) throws InsufficientFundsException {
            if(amount<=0)throw new InvalidAmountException("Amount must be positive");
            if(amount>balance)throw new InsufficientFundsException(amount-balance);
            balance-=amount;
        }
        long getBalance(){return balance;}
    }
    public static void main(String[] args) {
        Account a=new Account(1000);
        try { a.withdraw(5000); }
        catch(InsufficientFundsException e){System.out.println("Withdrawal failed: short by "+e.getShortfall());}
        catch(InvalidAmountException e){System.out.println(e.getMessage());}
        finally {System.out.println("Current balance: Rs."+a.getBalance());System.out.println("Transaction attempt completed.");}
    }
}
