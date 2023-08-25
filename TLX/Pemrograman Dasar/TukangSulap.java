import java.util.Scanner;

public class TukangSulap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Integer a[] = new Integer[n + 1];
        Integer b[] = new Integer[n + 1];

        for (int i = 1; i <= n; i++) a[i] = sc.nextInt();
        for (int i = 1; i <= n; i++) b[i] = sc.nextInt();

        int t = sc.nextInt();

        while(t-- > 0) {
            String c1 = sc.next();
            int n1 = sc.nextInt();
            String c2 = sc.next();
            int n2 = sc.nextInt();

            int temp;

            if (c1.equals("A") && c1.equals(c2)) {
                temp = a[n1];
                a[n1] = a[n2];
                a[n2] = temp;
            } else if (c1.equals("B") && c1.equals(c2)) {
                temp = b[n1];
                b[n1] = b[n2];
                b[n2] = temp;
            } else if (c1.equals("A") && c2.equals("B")) {
                temp = a[n1];
                a[n1] = b[n2];
                b[n2] = temp;
            } else {
                temp = a[n2];
                a[n2] = b[n1];
                b[n1] = temp;
            }
        }

        for (int i = 1; i <= n; i++) System.out.print(a[i] + " ");
        System.out.println();
        for (int i = 1; i <= n; i++) System.out.print(b[i] + " ");
    }
}
