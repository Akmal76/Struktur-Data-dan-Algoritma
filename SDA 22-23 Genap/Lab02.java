import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Lab02 {
    private static InputReader in;
    private static PrintWriter out;

    private static Stack <Integer> jumlahKue   = new Stack<Integer>();
    private static Stack <String>  rasaKue     = new Stack<String>();
    private static Map <String, Integer> total = new HashMap<String, Integer>();

    static int handleRestock(String Ri, int Xi) {
        jumlahKue.push(Xi);
        rasaKue.push(Ri);

        int sum = 0;
        Iterator <Integer> jumlah = jumlahKue.iterator();
        while (jumlah.hasNext()) sum += jumlah.next();

        return sum;
    }

    static String handleMakan(int Yi) {
        int temp = 0;

        while (Yi > 0) {
            if (jumlahKue.peek() >= Yi) {
                temp = jumlahKue.pop() - Yi;
                if (temp > 0) jumlahKue.push(temp);
                
                if (total.get(rasaKue.peek()) == null) total.put(rasaKue.peek(), Yi);
                else                                   total.put(rasaKue.peek(), total.get(rasaKue.peek()) + Yi);

                return rasaKue.peek();

            } else {
                temp = jumlahKue.pop();

                Yi -= temp;

                if (total.get(rasaKue.peek()) == null) total.put(rasaKue.peek(), temp);
                else                                   total.put(rasaKue.peek(), total.get(rasaKue.peek()) + temp);

                rasaKue.pop();
            }
        }

        return rasaKue.peek();
    }

    static int handleTotal(String Ri) {
        if (total.get(Ri) == null) return 0;
        return total.get(Ri);
    }


    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("RESTOCK")) {
                String Ri = in.next();
                int Xi = in.nextInt();

                out.println(handleRestock(Ri, Xi));
            } else if(event.equals("MAKAN")) {
                int Yi = in.nextInt();
                
                out.println(handleMakan(Yi));
            } else {
                String Ri = in.next();

                out.println(handleTotal(Ri));
            }
        }

        out.flush();
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
 
    }
}