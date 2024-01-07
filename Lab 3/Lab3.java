import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Lab3 {
    private static InputReader in;
    private static PrintWriter out;
    private static long T;
    private static int X;
    private static int C;
    private static int Q;
    private static int Ci;
    private static String perintah;
    private static boolean kanan = true;
    private static ArrayDeque <Integer>              lantai = new ArrayDeque<>();
    private static ArrayDeque <ArrayDeque <Integer>> gedung = new ArrayDeque<>();

    // Metode GA
    static String GA() {
        kanan = !kanan;                 // Invert nilai kanan
        return (kanan) ? "KANAN" : "KIRI";
    }

    // Metode S
    static long S(int Si){
        // Daripada menggeser SOFITA, mending menggeser gedungnya
        if (gedung.isEmpty()) return -1;    // Kalau semua gedung sudah hancur
        lantai = gedung.getFirst();         // Gedung yang sedang dikunjungi (paling kiri)

        long sum = 0;
        while (Si-- > 0 && !lantai.isEmpty()) {  // Ambil sebanyak Si lantai jika gedung belum hancur
            int temp = lantai.poll();
            T -= temp;
            sum += temp;
        }
        
        if (kanan) {    // Arah ke kanan
            // Jika gedung lain tidak hancur, geser semua gedung ke kiri
            if (gedung.peekFirst() != null) gedung.addLast(gedung.pollFirst());
            // Jika gedung sekarang hancur, keluarkan dari Deque
            if (lantai.isEmpty()) gedung.pollLast();
        }
        else {          // Arah ke kiri
            // Jika gedung sekarang hancur, keluarkan dari Deque
            if (lantai.isEmpty()) gedung.pollFirst();
            // Jika gedung lain tidak hancur, geser semua gedung ke kanan
            if (gedung.peekLast() != null) gedung.addFirst(gedung.pollLast());
        }

        return sum;
    }

    // Template
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        
        // Read input
        T = in.nextLong();
        X = in.nextInt();
        C = in.nextInt();
        Q = in.nextInt();

        for (int i = 0; i < X; i++) {
            // Insert into ADT
            lantai = new ArrayDeque<>();
            for (int j = 0; j < C; j++) {
                Ci = in.nextInt();
                lantai.add(Ci);
            }
            gedung.add(lantai);
        }

        // Process the query
        for (int i = 0; i < Q; i++) {
            perintah = in.next();
            if (perintah.equals("GA")) {
                out.println(GA());
            } else if (perintah.equals("S")) {
                int Si = in.nextInt();
                long tempSi = S(Si);
                if   (T <= 0 || gedung.isEmpty()) out.println("MENANG");
                else out.println(tempSi);
            }
        }

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
                try                   { tokenizer = new StringTokenizer(reader.readLine()); }
                catch (IOException e) { throw new RuntimeException(e); }
            }
            return tokenizer.nextToken();
        }

        public int  nextInt()  { return Integer.parseInt(next()); }
        public long nextLong() { return Long.parseLong(next()); }
    }
}