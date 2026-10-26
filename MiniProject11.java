import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MiniProject11 {
    static class AccountRecord implements Serializable {
        private static final long serialVersionUID=1L;
        String accountNumber, owner; long balance; transient String sessionToken;
        AccountRecord(String n,String o,long b,String token){accountNumber=n;owner=o;balance=b;sessionToken=token;}
        @Override public String toString(){return accountNumber+" | "+owner+" | Rs."+balance+" | token="+sessionToken;}
    }
    public static void main(String[] args) throws IOException,ClassNotFoundException {
        Path file=Paths.get("accounts.dat");
        AccountRecord[] records={new AccountRecord("AC0001","Riya",5000,"TEMP123"),new AccountRecord("AC0002","Amit",3000,"TEMP456")};
        try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(file))){out.writeObject(records);}
        try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(file))){AccountRecord[] loaded=(AccountRecord[])in.readObject();for(AccountRecord a:loaded)System.out.println(a);}
        BasicFileAttributes attr=Files.readAttributes(file,BasicFileAttributes.class);
        System.out.println("Saved file size: "+attr.size()+" bytes");
    }
}
