import java.util.Scanner;

public class OperasiString {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        String s3 = sc.nextLine();
        String s4 = sc.nextLine();

        String res = s1.substring(0, s1.indexOf(s2)) + s1.substring(s1.indexOf(s2) + s2.length(), s1.length());
        res = res.substring(0, res.indexOf(s3)) + s3 + s4 + res.substring(res.indexOf(s3)+s3.length(), res.length());
        
        System.out.println(res);
    }
    
}
