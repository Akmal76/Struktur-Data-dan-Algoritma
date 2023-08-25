import java.util.Scanner;

public class KomposisiFungsi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();
        int k = sc.nextInt();
        int x = sc.nextInt();

        System.out.println(f(a, b, x, k));
    }

    public static int f(int a, int b, int x, int k) {
        if (k == 1) return Math.abs(a*x + b);
        return f(a, b, Math.abs(a*x + b), k-1);
    }
}
