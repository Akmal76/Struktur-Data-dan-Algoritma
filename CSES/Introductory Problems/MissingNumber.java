import java.util.Scanner;

public class MissingNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long n = sc.nextInt();

        long sum = n * (n + 1) / 2;
        long temp = 0;

        while(n-- > 1) temp += sc.nextInt();

        System.out.println(Math.abs(sum - temp));

        sc.close();
    }
}