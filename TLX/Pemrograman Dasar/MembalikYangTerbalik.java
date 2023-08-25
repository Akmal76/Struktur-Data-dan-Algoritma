import java.util.Scanner;

public class MembalikYangTerbalik {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String a = sc.next();
        String b = sc.next();

        System.out.println(Integer.parseInt(balik(Integer.toString(Integer.parseInt(balik(a)) + Integer.parseInt(balik(b))))));

    }

    public static String balik (String s) {
        String result = "";
        for (int i = s.length()-1; i >= 0; i--) result += s.charAt(i);
        return result;
    }
}
