import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        int k = sc.nextInt();

        String hasil = "";

        for (int i = 0; i < s.length(); i++) {
            hasil += Character.toString((char)(((int)s.charAt(i) + k - (int)'a') % 26) + (int)'a');
        }

        System.out.println(hasil);
    }
}
