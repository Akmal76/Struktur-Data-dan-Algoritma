import java.util.Scanner;

public class PerkalianMatriks {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();

        Integer a[][] = new Integer[n+1][m+1];
        Integer b[][] =  new Integer[m+1][p+1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                a[i][j] = sc.nextInt();
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= p; j++) {
                b[i][j] = sc.nextInt();
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= p; j++) {
                int c = 0;
                for (int k = 1; k <= m; k++) {
                    c += a[i][k] * b[k][j];
                }
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}
