import java.io.*;
import java.util.StringTokenizer;

public class Lab2 {
    private static InputReader in;
    private static PrintWriter out;

    static long maxOddEvenSubSum(long[] a) {
        long maxSum = Long.MIN_VALUE;
        long nowSum = Long.MIN_VALUE;

        for (int i = 0; i < a.length; i++) {
            // Jika genap/ganjil panjang array sama dengan genap/ganjil angka ke-i
            if (Math.abs(a[i]) % 2 == a.length % 2) {

                // Jika bilangan sebelumnya diambil tapi nowSum masih positif berarti ambil aja
                if (nowSum >= 0)              nowSum += a[i];
                // Jika bilangan sebelumnya diambil tapi kalau nowSum negatif, mending buang ganti dengan sequence baru
                else                          nowSum = a[i];

                maxSum = Math.max(maxSum, nowSum);

            } else nowSum = Long.MIN_VALUE; // Bilangan ini diskip, langsung aja buat nowSum negatif
        }

        if (maxSum == Long.MIN_VALUE) return 0;
        // Kalau ini terpenuhi artinya nilai maxSum diatas gak terupdate artinya tidak ada yang memenuhi syarat
        // Panjang array genap tapi isinya bilangan ganjil semua atau sebaliknya
        
        return maxSum;
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N
        int N = in.nextInt();

        // Read value of x
        long[] x = new long[N];
        for (int i = 0; i < N; ++i) {
            x[i] = in.nextLong();
        }

        long ans = maxOddEvenSubSum(x);
        out.println(ans);

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

        public long nextLong() {
            return Long.parseLong(next());
        }

    }
}