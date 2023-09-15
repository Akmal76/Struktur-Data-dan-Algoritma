import java.util.Scanner;

public class P_22_A_Pecahan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long a = sc.nextLong();
        long b = sc.nextLong();
        long c = sc.nextLong();
        long d = sc.nextLong();

        if (a * d == b * c)     System.out.println("sama");
        else if (a * d > b * c) System.out.println("lebih besar");
        else                    System.out.println("lebih kecil");

        sc.close();
    }
}