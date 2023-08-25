import java.util.Scanner;

public class RotasiMatriks {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        Integer matrix[][] = new Integer[101][101];

        for (int i = 1; i <= n; i++){
            for (int j = 1; j <= m; j++) {
                int num = sc.nextInt();
                matrix[i][j] = num;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= 1; j--) System.out.print(matrix[j][i] + " ");
            System.out.println();
        }
    }
}
