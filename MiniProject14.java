import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MiniProject14 {
    static class Account implements Serializable {
        private static final long serialVersionUID=1L;
        final String number; final String owner; private long balance;
        Account(String n,String o,long b){number=n;owner=o;balance=b;}
        synchronized void deposit(long amount){if(amount<=0)throw new IllegalArgumentException("Amount must be positive");balance+=amount;}
        synchronized void withdraw(long amount){if(amount<=0)throw new IllegalArgumentException("Amount must be positive");if(amount>balance)throw new IllegalStateException("Insufficient funds; balance is Rs."+balance);balance-=amount;}
        synchronized long getBalance(){return balance;}
        @Override public String toString(){return number+" | "+owner+" | Rs."+getBalance();}
    }
    static class BankService {
        private final Map<String,Account> accounts=new LinkedHashMap<>();
        private final Path file=Paths.get("minibank-accounts.dat");
        BankService(){load();}
        Account openAccount(String owner,long opening){if(opening<0)throw new IllegalArgumentException("Opening balance cannot be negative");String id=String.format("AC%04d",accounts.size()+1);Account a=new Account(id,owner,opening);accounts.put(id,a);return a;}
        Account find(String id){Account a=accounts.get(id);if(a==null)throw new IllegalArgumentException("Account not found");return a;}
        void deposit(String id,long amount){find(id).deposit(amount);}
        void withdraw(String id,long amount){find(id).withdraw(amount);}
        void transfer(String from,String to,long amount){Account a=find(from),b=find(to);if(a==b)throw new IllegalArgumentException("Choose two different accounts");synchronized(a){synchronized(b){a.withdraw(amount);b.deposit(amount);}}}
        void statement(String id){System.out.println("MINIBANK STATEMENT\n"+find(id));}
        void list(){if(accounts.isEmpty())System.out.println("No accounts available.");else accounts.values().forEach(System.out::println);}
        void save(){try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(file))){out.writeObject(new ArrayList<>(accounts.values()));System.out.println("Accounts saved.");}catch(IOException e){System.out.println("Save failed: "+e.getMessage());}}
        @SuppressWarnings("unchecked") void load(){if(!Files.exists(file))return;try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(file))){for(Account a:(ArrayList<Account>)in.readObject())accounts.put(a.number,a);}catch(IOException|ClassNotFoundException e){System.out.println("Could not load saved accounts: "+e.getMessage());}}
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);BankService bank=new BankService();
        while(true){
            System.out.println("\n===== MINIBANK =====\n1.Open account\n2.Deposit\n3.Withdraw\n4.Transfer\n5.Statement\n6.List accounts\n7.Save and exit\n");
            System.out.print("Choose: ");String choice=sc.nextLine().trim();
            try{
                switch(choice){
                    case "1"->{System.out.print("Owner name: ");String name=sc.nextLine();System.out.print("Opening balance: ");long amount=Long.parseLong(sc.nextLine());System.out.println("Created: "+bank.openAccount(name,amount));}
                    case "2"->{System.out.print("Account number: ");String id=sc.nextLine();System.out.print("Amount: ");bank.deposit(id,Long.parseLong(sc.nextLine()));System.out.println("Deposit successful.");}
                    case "3"->{System.out.print("Account number: ");String id=sc.nextLine();System.out.print("Amount: ");bank.withdraw(id,Long.parseLong(sc.nextLine()));System.out.println("Withdrawal successful.");}
                    case "4"->{System.out.print("From account: ");String from=sc.nextLine();System.out.print("To account: ");String to=sc.nextLine();System.out.print("Amount: ");bank.transfer(from,to,Long.parseLong(sc.nextLine()));System.out.println("Transfer successful.");}
                    case "5"->{System.out.print("Account number: ");bank.statement(sc.nextLine());}
                    case "6"->bank.list();
                    case "7"->{bank.save();System.out.println("Goodbye!");sc.close();return;}
                    default->System.out.println("Invalid choice.");
                }
            }catch(Exception e){System.out.println("Operation failed: "+e.getMessage());}
        }
    }
}
