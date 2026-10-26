public class MiniProject3 {
    static class Account {
        final String accountNumber; String ownerName; long balance; boolean active = true;
        Account(String no, String owner, long balance) { this.accountNumber=no; this.ownerName=owner; this.balance=balance; }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + balance + " | active=" + active; }
        @Override public boolean equals(Object obj) { return obj instanceof Account other && accountNumber.equals(other.accountNumber); }
        @Override public int hashCode() { return accountNumber.hashCode(); }
        static class Address {
            String line, city, pincode;
            Address(String line, String city, String pincode) { this.line=line; this.city=city; this.pincode=pincode; }
            public String getCity() { return city; }
        }
    }
    static class Customer implements Cloneable {
        String name; Account.Address address;
        Customer(String name, Account.Address address) { this.name=name; this.address=address; }
        @Override public Customer clone() { return new Customer(name, new Account.Address(address.line, address.city, address.pincode)); }
    }
    public static void main(String[] args) {
        Account a1=new Account("AC0001","Riya",4000), a2=new Account("AC0001","Riya Shah",4000);
        System.out.println(a1); System.out.println("Accounts equal: " + a1.equals(a2));
        System.out.println("Is Account: " + (a1 instanceof Account));
        Customer c1=new Customer("Riya",new Account.Address("Main Road","Anand","388001"));
        Customer c2=c1.clone(); System.out.println(c2.name + " lives in " + c2.address.getCity());
    }
}
