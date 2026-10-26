import java.util.concurrent.*;

public class MiniProject10 {
    static class Account {
        final String number; private long balance; private final Object lock=new Object();
        Account(String n,long b){number=n;balance=b;}
        void deposit(long n){synchronized(lock){if(n>0)balance+=n;}}
        boolean withdraw(long n){synchronized(lock){if(n>0&&balance>=n){balance-=n;return true;}return false;}}
        long getBalance(){synchronized(lock){return balance;}}
        static void transfer(Account a,Account b,long amount){Account first=a.number.compareTo(b.number)<0?a:b;Account second=first==a?b:a;synchronized(first.lock){synchronized(second.lock){if(amount>0&&a.balance>=amount){a.balance-=amount;b.balance+=amount;}}}}
    }
    static class TransactionProcessor {
        private final ExecutorService pool=Executors.newFixedThreadPool(4);
        void submit(Runnable task){pool.execute(task);}
        void stop() throws InterruptedException {pool.shutdown();if(!pool.awaitTermination(10,TimeUnit.SECONDS))pool.shutdownNow();}
    }
    public static void main(String[] args) throws InterruptedException {
        Account a=new Account("AC01",10000),b=new Account("AC02",5000);
        TransactionProcessor processor=new TransactionProcessor();
        for(int i=0;i<20;i++){final int n=i;processor.submit(()->{if(n%2==0)a.deposit(100);else a.withdraw(50);});}
        processor.stop();
        Account.transfer(a,b,1000);
        System.out.println("Account A: "+a.getBalance());System.out.println("Account B: "+b.getBalance());
        System.out.println("Total balance: "+(a.getBalance()+b.getBalance()));
    }
}
