import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        
        System.out.println("Part a: Sum of Integers Using Autoboxing and Unboxing");
        System.out.print("Enter the number of integers: ");
        int count = Integer.parseInt(scanner.nextLine());
        
        for (int i = 0; i < count; i++) {
            System.out.print("Enter integer " + (i + 1) + ": ");
            String input = scanner.nextLine();
            numbers.add(Integer.parseInt(input));
        }
        
        int sum = 0;
        for (Integer num : numbers) {
            sum += num;
        }
        
        System.out.println("Total sum: " + sum);
        
        scanner.close();
    }
}
