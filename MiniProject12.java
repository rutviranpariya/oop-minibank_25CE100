import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MiniProject12 {
    static class Account {
        final String number, owner; private final AtomicLong balance;
        Account(String n,String o,long b){number=n;owner=o;balance=new AtomicLong(b);}
        void deposit(long amount){if(amount>0)balance.addAndGet(amount);}
        boolean withdraw(long amount){if(amount<=0)return false;while(true){long old=balance.get();if(old<amount)return false;if(balance.compareAndSet(old,old-amount))return true;}}
        long getBalance(){return balance.get();}
    }
    static class AccountStore {
        private final ConcurrentHashMap<String,Account> accounts=new ConcurrentHashMap<>();
        void save(Account a){accounts.put(a.number,a);} Account find(String id){return accounts.get(id);}
        boolean delete(String id){return accounts.remove(id)!=null;} void printAll(){accounts.values().forEach(System.out::println);}
    }
    public static void main(String[] args) throws InterruptedException {
        Account a=new Account("AC0001","Riya",0); AccountStore store=new AccountStore();store.save(a);
        Thread[] ts=new Thread[10];for(int i=0;i<10;i++){ts[i]=new Thread(()->{for(int j=0;j<1000;j++)a.deposit(1);});ts[i].start();}
        for(Thread t:ts)t.join();System.out.println("Thread-safe balance: "+store.find("AC0001").getBalance());
    }
}
