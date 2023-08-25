import java.util.Scanner;

public class MenggambarPegunungan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        gunung(n);
    }

    public static void gunung (int n) {
        if (n == 1) { System.out.println("*"); return; }

        gunung(n-1);
        
        for (int i = 1; i <= n; i++) System.out.print("*");
        System.out.println();

        gunung(n-1);

        return;
    }
}
