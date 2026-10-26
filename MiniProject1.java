import java.util.Scanner;

public class MiniProject1 {
    record BankInfo(String name, String branch) {}
    enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(new BankInfo("MiniBank", "CHARUSAT Branch"));
        int choice;
        do {
            System.out.println("\n1. Open account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Exit");
            System.out.print("Choose: ");
            while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Open account - coming in Practical 2");
                case 2 -> System.out.println("Deposit - coming in a later practical");
                case 3 -> System.out.println("Withdraw - coming in a later practical");
                case 4 -> System.out.println("Transfer - coming in a later practical");
                case 5 -> System.out.println("Thank you for using MiniBank.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
        sc.close();
    }
}
