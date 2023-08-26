import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab01_23 {
    private static InputReader in;
    private static PrintWriter out;

    public static int findMaxLength(String[] chars) {
        int[] sumArray = new int [2 * chars.length + 1];
        Arrays.fill(sumArray, Integer.MIN_VALUE);

        sumArray[chars.length] = -1;

        int maxLength = 0 , count = 0;
        
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equals("L")) count += 1;
            else                               count -= 1;

            int indeks = chars.length + count;
            if (sumArray[indeks] >= -1) maxLength = Math.max(maxLength, i - sumArray[indeks]);
            else                        sumArray[count + chars.length] = i;
        }

        return maxLength;
    }

    /*
     * 0
     * -5 -4 -3 -2 -1 0 1 2 3 4 5
     *  ~  ~  ~  ~  ~ 0 0 ~ ~ ~ ~
     */

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N
        int N = in.nextInt();

        // Read value of H
        String[] H = new String[N];
        for (int i = 0; i < N; ++i) {
            H[i] = in.next();
        }

        out.println(findMaxLength(H));

        // don't forget to close/flush the output
        out.close();
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