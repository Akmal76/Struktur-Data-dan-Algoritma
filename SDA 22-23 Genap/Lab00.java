import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab00 {
    private static InputReader in;
    private static PrintWriter out;

    static long calcMoney(int M, int[] H) {
        long answer = 0;

        for (int i = 0; i < M; i++) answer += H[i];

        return answer;
    }


    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of M
        int M = in.nextInt();

        // Read value of H
        int[] H = new int[M];
        for (int i = 0; i < M; ++i) {
            H[i] = in.nextInt();
        }

        out.println(calcMoney(M, H));

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