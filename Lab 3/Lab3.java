import java.io.*;
import java.util.*;

public class Lab3{
    private static InputReader in;
    private static PrintWriter out;

    private static long T;
    private static int X;
    private static int C;
    private static int Q;
    private static int Ci;
    private static String perintah;

    private static boolean kanan = true;
    private static boolean menang = false;

    private static LinkedList <Integer> lantai = new LinkedList<>();
    private static LinkedList <LinkedList <Integer>> gedung = new LinkedList<>();

    // Metode GA
    static String GA() {
        //TODO: Implement this method
        if (kanan) { 
            kanan = false; 
            return "KIRI"; 
        }
        
        kanan = true; return "KANAN";
    }

    // Metode S
    static long S(int Si){
        //TODO: Implement this method
        if (menang) return Long.MIN_VALUE;
        if (gedung.isEmpty()) return Long.MIN_VALUE;
        if (gedung.getFirst() == null) return Long.MIN_VALUE;
 
        lantai = gedung.getFirst();

        long sum = 0;

        while (Si-- > 0 && !lantai.isEmpty()) {
            long temp = lantai.pop();
            T -= temp;
            sum += temp;
        }

        if  (kanan) gedung.addLast(gedung.pollFirst());
        else        gedung.addFirst(gedung.pollLast());

        if (lantai.isEmpty()) gedung.remove(lantai);

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
            lantai = new LinkedList<>();
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

                if (T <= 0 || tempSi == Long.MIN_VALUE || gedung.isEmpty()) out.println("MENANG");
                else out.println(tempSi);
            }
        }

        // don't forget to close the output
        out.close();
    }
    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
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

        public long nextLong(){
            return Long.parseLong(next());
        }

    }
}