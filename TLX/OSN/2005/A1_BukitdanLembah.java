import java.util.Scanner;

public class A1_BukitdanLembah {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long mini = 1000000001;
        long maks = -1;

        boolean status = true;
        long n = sc.nextLong();

        while (sc.hasNextLong()) {
            long m = sc.nextLong();

            if (m > n) {
                status = true;
            } else if (m < n) {
                status = false;
            }
        }
    }
}