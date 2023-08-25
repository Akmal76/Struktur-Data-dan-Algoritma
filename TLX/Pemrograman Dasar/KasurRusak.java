import java.util.Scanner;

public class KasurRusak {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        if (palindrome(s)) System.out.println("YA");
        else System.out.println("BUKAN");
    }

    public static boolean palindrome(String s) {
        if (s.length() == 0 || s.length() == 1) return true;
        return (s.charAt(0) == s.charAt(s.length()-1)) && palindrome(s.substring(1, s.length()-1));
    }
}
