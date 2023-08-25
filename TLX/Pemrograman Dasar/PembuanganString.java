import java.util.Scanner;

public class PembuanganString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.next();
        String t = sc.next();

        while(s.contains(t)){
            int indeks = s.indexOf(t);
            s = s.substring(0, indeks) + s.substring(indeks+t.length(), s.length());
        }

        System.out.println(s);
    }
}
