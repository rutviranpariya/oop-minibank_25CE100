public class MiniProject2 {
    static class Customer {
        private static long counter = 100;
        private final String customerId;
        private final String name, email, mobile;
        Customer(String name, String email, String mobile) {
            this.customerId = "CUST" + (++counter);
            this.name = name; this.email = email; this.mobile = mobile;
        }
        public String getCustomerId() { return customerId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMobile() { return mobile; }
    }
    static class Account {
        private static int counter = 1;
        private final String accountNumber = String.format("AC%04d", counter++);
        private final String ownerName;
        private long balance;
        private boolean active = true;
        Account(String ownerName) { this(ownerName, 0); }
        Account(String ownerName, long openingBalance) {
            this.ownerName = ownerName; this.balance = Math.max(0, openingBalance);
        }
        public void deposit(long amount) { if (active && amount > 0) balance += amount; }
        public boolean withdraw(long amount) {
            if (active && amount > 0 && balance >= amount) { balance -= amount; return true; }
            return false;
        }
        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName() { return ownerName; }
        public long getBalance() { return balance; }
        public boolean isActive() { return active; }
    }
    public static void main(String[] args) {
        Customer c = new Customer("Riya Shah", "riya@example.com", "9876543210");
        Account[] accounts = { new Account(c.getName(), 5000), new Account("Amit", 2000), new Account("Neha") };
        accounts[0].deposit(1000); accounts[0].withdraw(500); accounts[1].withdraw(3000);
        System.out.println(c.getCustomerId() + " | " + c.getName() + " | " + c.getEmail());
        for (Account a : accounts) System.out.println(a.getAccountNumber() + " | " + a.getOwnerName() + " | Balance: " + a.getBalance());
    }
}
