import java.io.*;
import java.util.StringTokenizer;

public class Lab01 {
    private static InputReader in;
    private static PrintWriter out;

    static boolean check (int x, int [] letterCount) {
        // Cek apakah huruf S, O, F, I, T, A ini dibutuhkan atau tidak
        for (int i = 0; i < x; i++) { if (letterCount[x] + 1 > letterCount[i]) return true; }
        return false;
    }

    static int getTotalDeletedLetters(int N, char[] x) {
        
        int wordCount = 0; // Jumlah kata SOFITA yang dapat dibentuk
        int [] letterCount = new int [6];

        for (int i = 0; i < N; i++) {
            if      (x[i] == 'S') letterCount[0]++;
            else if (x[i] == 'O' && !check(1, letterCount)) letterCount[1]++;
            else if (x[i] == 'F' && !check(2, letterCount)) letterCount[2]++;
            else if (x[i] == 'I' && !check(3, letterCount)) letterCount[3]++;
            else if (x[i] == 'T' && !check(4, letterCount)) letterCount[4]++;
            else if (x[i] == 'A' && !check(5, letterCount)) {letterCount[5]++; wordCount++;}
        }

        return N - wordCount * 6;
    }

    // Fungsi utama berjalannya program
    public static void main(String[] args) throws IOException {
        // Melakukan inisiasi input dan output
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Membaca nilai dari N (total input yang ingin dimasukkan)
        int N = in.nextInt();

        // Read value of x
        char[] x = new char[N];
        for (int i = 0; i < N; ++i) {
            x[i] = in.next().charAt(0);
        }

        // Melakukan pencetakan total deleted letters
        int ans = getTotalDeletedLetters(N, x);
        out.println(ans);

        // Tutup out
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