import java.util.*;

public class FaktorialGanjilGenap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        System.out.println(n_fact(n));

    }

    public static int n_fact (int n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return n/2 * n_fact(n-1);
        return n * n_fact(n-1);
    }
}
