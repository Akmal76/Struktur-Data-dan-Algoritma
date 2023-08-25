import java.util.Scanner;

public class IncreasingArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();
        long prev = sc.nextLong();
        long result = 0;

        while(n-- > 1) {
            long now = sc.nextLong();
            if (now < prev) {
                result += prev - now;
                now += prev - now;
            }
            prev = now;
        }

        System.out.println(result);
    }
}