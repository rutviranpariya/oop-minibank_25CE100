public class MiniProject5 {
    static abstract class Account {
        protected String number, owner; protected long balance;
        Account(String n,String o,long b){number=n;owner=o;balance=b;}
        abstract double interestRate();
        public void deposit(long amount){if(amount>0)balance+=amount;}
        public long getBalance(){return balance;}
        @Override public String toString(){return number+" | "+owner+" | balance="+balance+" | rate="+interestRate()+"%";}
    }
    static class SavingsAccount extends Account { SavingsAccount(String n,String o,long b){super(n,o,b);} @Override double interestRate(){return 4.0;} }
    static class CurrentAccount extends Account { CurrentAccount(String n,String o,long b){super(n,o,b);} @Override double interestRate(){return 0.0;} }
    static class FixedDepositAccount extends Account { FixedDepositAccount(String n,String o,long b){super(n,o,b);} @Override double interestRate(){return 7.0;} }
    public static void main(String[] args) {
        Account[] accounts={new SavingsAccount("AC01","Riya",5000),new CurrentAccount("AC02","Amit",8000),new FixedDepositAccount("AC03","Neha",12000)};
        for(Account a:accounts)System.out.println(a);
        Object obj=accounts[0]; if(obj instanceof SavingsAccount s)System.out.println("Savings account balance: "+s.getBalance());
    }
}
