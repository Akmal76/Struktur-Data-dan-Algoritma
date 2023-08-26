import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lab01 {
    private static InputReader in;
    private static PrintWriter out;

    static int getTotalDeletedLetters(int N, char[] x) {
        // Hitung semua kemunculan huruf
        int[] freqLetter = new int[6];
        Arrays.fill(freqLetter, 0);

        for (int i = 0; i < N; i++) {
            if      (x[i] == 'S') freqLetter[0]++;
            else if (x[i] == 'O') freqLetter[1]++;
            else if (x[i] == 'F') freqLetter[2]++;
            else if (x[i] == 'I') freqLetter[3]++;
            else if (x[i] == 'T') freqLetter[4]++;
            else if (x[i] == 'A') freqLetter[5]++;
        }

        // Kasus apabila salah satu dari keenam huruf tersebut tidak ada
        if (freqLetter[0] == 0 || freqLetter[1] == 0 || freqLetter[2] == 0 || freqLetter[3] == 0 || freqLetter[4] == 0 || freqLetter[5] == 0) return N;

        // Simpan semua indeks untuk keenam huruf
        int[][] indexLetter = new int[6][N];
        int[]   countLetter = new int[6];

        Arrays.fill(countLetter, 0);

        for (int i = 0; i < N; i++) {
            if      (x[i] == 'S') {indexLetter[0][countLetter[0]] = i; countLetter[0]++;}
            else if (x[i] == 'O') {indexLetter[1][countLetter[1]] = i; countLetter[1]++;}
            else if (x[i] == 'F') {indexLetter[2][countLetter[2]] = i; countLetter[2]++;}
            else if (x[i] == 'I') {indexLetter[3][countLetter[3]] = i; countLetter[3]++;}
            else if (x[i] == 'T') {indexLetter[4][countLetter[4]] = i; countLetter[4]++;}
            else if (x[i] == 'A') {indexLetter[5][countLetter[5]] = i; countLetter[5]++;}
        }

        // Mencari banyaknya kalimat SOVITA
        Arrays.fill(countLetter, 0);
        int countSovita = 0;

        while (countLetter[0] < freqLetter[0] && countLetter[1] < freqLetter[1] && countLetter[2] < freqLetter[2] && countLetter[3] < freqLetter[3] && countLetter[4] < freqLetter[4] && countLetter[5] < freqLetter[5]) {
            if (indexLetter[0][countLetter[0]] < indexLetter[1][countLetter[1]]) {
                if (indexLetter[1][countLetter[1]] < indexLetter[2][countLetter[2]]) {
                    if (indexLetter[2][countLetter[2]] < indexLetter[3][countLetter[3]]) {
                        if (indexLetter[3][countLetter[3]] < indexLetter[4][countLetter[4]]) {
                            if (indexLetter[4][countLetter[4]] < indexLetter[5][countLetter[5]]) {
                                countSovita++;
                                countLetter[0]++; countLetter[1]++; countLetter[2]++; countLetter[3]++; countLetter[4]++; countLetter[5]++; 
                            } else countLetter[5]++;
                        } else countLetter[4]++;
                    } else countLetter[3]++;
                } else countLetter[2]++;
            } else countLetter[1]++;
        }

        return N - countSovita * 6;
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