import java.util.Scanner;

public class KonversiBiner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();

        System.out.println(biner(n));
    }

    public static String biner (long n) {
        if (n == 1) return "1";
        if (n % 2 == 0) return biner(n/2) + "0";
        return biner(n/2) + "1";
    }
}
