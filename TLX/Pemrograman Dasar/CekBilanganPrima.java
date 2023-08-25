import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CekBilanganPrima {

    private static InputReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

       int n = in.nextInt();
       
       while (n-- > 0) {
        int a = in.nextInt();
        if (a == 1) System.out.println("BUKAN");
        else if (a == 2) System.out.println("YA");
        else {
            boolean prima = true;
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) {
                    System.out.println("BUKAN");
                    prima = false;
                    break;
                }
            }
            if (prima) System.out.println("YA");
        }
       }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}