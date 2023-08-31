import java.io.*;
import java.util.*;

public class Lab01 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    /**
    * The main method that reads input, calls the function
    * for each question's query, and output the results.
    * @param args Unused.
    * @return Nothing.
    */
    public static void main(String[] args) {
            int N = in.nextInt(); // banyak bintang
            int M = in.nextInt(); // panjang sequence
            List<String> sequence = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                String temp = in.next();
                sequence.add(temp);
            }
            int maxMoney = getMaxMoney(N, M, sequence);
            out.println(maxMoney);
            out.close();
        }

        public static int getMaxMoney(int N, int M, List<String> sequence) {
            int result = -1000000;
            int miniPrefixSum = 0;
            int [] pendapatan = new int [N - 1];

            int indeks = 0, sum = 0;
            for (int i = 1; i < sequence.size(); i++) {
                if (sequence.get(i).equals("*")) {
                    pendapatan[indeks] = sum;
                    sum = 0;
                    indeks++;
                } else sum += Integer.parseInt(sequence.get(i));
            }

            int [] prefixSum = new int [N - 1];
            prefixSum[0] = pendapatan[0];

            for (int i = 1; i < N-1; i++) prefixSum[i] = prefixSum[i-1] + pendapatan[i];
            for (int i = 0; i < N-1; i++) {
                result = Math.max(result, prefixSum[i] - miniPrefixSum);
                miniPrefixSum = Math.min(miniPrefixSum, prefixSum[i]);
            }

            return result;
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