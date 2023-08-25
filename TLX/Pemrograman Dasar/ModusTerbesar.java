import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ModusTerbesar {

    private static InputReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        Integer data[] = new Integer[1001];

        int n = sc.nextInt();

        for (int i = 1; i <= 1000; i++) data[i] = 0;

        for (int i = 1; i <= n; i++) {
            int a = sc.nextInt();
            data[a]++;
        }

        int maks = 0, modus = 0;

        for (int i = 1; i <= 1000; i++) {
            if (data[i] >= maks) {
                maks = data[i];
                modus = i;
            }
        }

        System.out.println(modus);

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