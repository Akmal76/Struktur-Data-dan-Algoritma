import java.util.*;

public class BahasaDengklek {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        int interval = (int)'a' - (int)'A';

        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) <= 'Z') System.out.print(Character.toChars((int)s.charAt(i) + interval));
            else System.out.print(Character.toChars((int)s.charAt(i) - interval));
        }
    }
}
