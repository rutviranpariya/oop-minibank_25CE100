import java.util.regex.Pattern;

public class MiniProject4 {
    static class Validator {
        static boolean isValidMobile(String s) { return Pattern.matches("[6-9][0-9]{9}", s); }
        static boolean isValidEmail(String s) { return Pattern.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}", s); }
        static boolean isValidPan(String s) { return Pattern.matches("[A-Z]{5}[0-9]{4}[A-Z]", s); }
        static boolean isValidIfsc(String s) { return Pattern.matches("[A-Z]{4}0[A-Z0-9]{6}", s); }
    }
    enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }
    record Command(TransactionType type, String accountNumber, long amount) {}
    static class CommandParser {
        static Command parse(String line) {
            String[] p=line.trim().split("\\s+");
            if (p.length != 3) throw new IllegalArgumentException("Use: DEPOSIT AC0001 500");
            return new Command(TransactionType.valueOf(p[0].toUpperCase()), p[1], Long.parseLong(p[2]));
        }
    }
    static class Account { String number, owner; long balance; Account(String n,String o,long b){number=n;owner=o;balance=b;} }
    static class StatementFormatter {
        static String buildStatement(Account a) { return "MINIBANK STATEMENT\nAccount: "+a.number+"\nOwner: "+a.owner+"\nBalance: Rs."+a.balance; }
    }
    public static void main(String[] args) {
        System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
        System.out.println("Email valid: " + Validator.isValidEmail("riya@example.com"));
        System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("IFSC valid: " + Validator.isValidIfsc("ABCD0123456"));
        Command c=CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println(c.type()+" | "+c.accountNumber()+" | "+c.amount());
        System.out.println(StatementFormatter.buildStatement(new Account("AC0001","Riya",4500)));
    }
}
