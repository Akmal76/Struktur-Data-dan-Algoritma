import java.util.Scanner;

public class TemanDekat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int d = sc.nextInt();

        Integer a[] = new Integer[1001];
        Integer b[] = new Integer[1001];

        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
        }

        int min = 1000000000;
        int max = -1000000000;

        for (int i = 1; i <= n; i++) {
            for (int j = i+1; j <= n; j++) {
                int relate = t(a[i], a[j], b[i], b[j], d);

                if (relate > max) max = relate;
                if (relate < min) min = relate;
            }
        }

        System.out.println(min + " " + max);
    }

    public static int t(int xj, int xi, int yj, int yi, int d){
        return (int) Math.pow(Math.abs(xj - xi), d) + (int) Math.pow(Math.abs(yj - yi), d);
    }
}
