import java.util.Scanner;

public class Repetitions {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String dna = sc.nextLine();

        int count = 0, result = 0;
        for (int i = 1; i < dna.length(); i++) {
            if (dna.charAt(i) == dna.charAt(i-1)) {
                count++;
            } else {
                if (count > result) result = count;
                count = 0;
            }
        }
        
        if (count > result) result = count;

        System.out.println(result + 1);

        sc.close();
    }
}