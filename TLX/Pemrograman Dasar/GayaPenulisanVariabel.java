import java.util.*;

public class GayaPenulisanVariabel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        
        if (s.contains("_")) while (s.contains("_")) s = s.substring(0, s.indexOf("_")) + Character.toUpperCase(s.charAt(s.indexOf("_") + 1)) + s.substring(s.indexOf("_") + 2, s.length());
        else {
            String result = "";
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                    result += "_" + Character.toLowerCase(s.charAt(i));
                } else result += s.charAt(i);
            }
            s = result;
        }

        System.out.println(s);
    }
}
