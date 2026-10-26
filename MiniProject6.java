public class MiniProject6 {
    interface WithdrawRule { boolean mayWithdraw(long balance,long amount); }
    interface Transferable { boolean transferTo(Account target,long amount); }
    static class Account implements Transferable {
        private final String number; private long balance;
        Account(String number,long balance){this.number=number;this.balance=balance;}
        public void deposit(long amount){if(amount>0)balance+=amount;}
        public boolean withdraw(long amount){if(amount>0 && balance>=amount){balance-=amount;return true;}return false;}
        @Override public boolean transferTo(Account target,long amount){if(withdraw(amount)){target.deposit(amount);return true;}return false;}
        public long getBalance(){return balance;} public String getNumber(){return number;}
    }
    public static void main(String[] args) {
        Account a=new Account("AC01",5000), b=new Account("AC02",1000);
        WithdrawRule standard=new WithdrawRule(){public boolean mayWithdraw(long balance,long amount){return amount>0 && amount<=balance;}};
        WithdrawRule dailyLimit=(balance,amount)->amount>0 && amount<=balance && amount<=2000;
        long amount=1500;
        if(standard.mayWithdraw(a.getBalance(),amount) && dailyLimit.mayWithdraw(a.getBalance(),amount))a.transferTo(b,amount);
        System.out.println(a.getNumber()+" balance: "+a.getBalance());
        System.out.println(b.getNumber()+" balance: "+b.getBalance());
    }
}
