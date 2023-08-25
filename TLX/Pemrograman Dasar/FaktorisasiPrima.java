import java.util.Scanner;

public class FaktorisasiPrima {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        // Generate Prime
        Integer prima[] = new Integer[78499];

        int index = 0;
        for (int i = 2; i <= 1000000; i++) {
            boolean isPrime = true;

            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                // System.out.println(i);
                prima[index] = i;
                index++;
            }
        }

        // Find Factor
        for (int i = 0; i <= 78498; i++) {
            int pangkat = 0;
            while (n % prima[i] == 0) {
                n /= prima[i];
                pangkat++;
            }

            if (pangkat == 1) System.out.print(prima[i]);
            else if (pangkat > 1) System.out.print(prima[i] + "^" + pangkat);

            if (n == 1) break;
            else if (pangkat > 0) System.out.print(" x ");
        }

    }
}
