import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Note:
 * 1. Mahasiswa tidak diperkenankan menggunakan data struktur dari library seperti ArrayList, LinkedList, dll.
 * 2. Mahasiswa diperkenankan membuat/mengubah/menambahkan class, class attribute, instance attribute, tipe data, dan method yang sekiranya perlu untuk menyelesaikan permasalahan.
 * 3. Mahasiswa dapat menggunakan method {@code traverse()} dari class {@code DoublyLinkedList} untuk membantu melakukan print statement debugging.
 */
public class Lab5 {

    private static InputReader in;
    private static PrintWriter out;
    private static DoublyLinkedList rooms = new DoublyLinkedList();

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N = in.nextInt();

        for (int i = 0; i < N; i++) {
            char command = in.nextChar();
            char direction;

            switch (command) {
                case 'A':
                    direction = in.nextChar();
                    char type = in.nextChar();
                    add(type, direction);
                    break;
                case 'D':
                    direction = in.nextChar();
                    out.println(delete(direction));
                    break;
                case 'M':
                    direction = in.nextChar();
                    out.println(move(direction));
                    break;
                case 'J':
                    direction = in.nextChar();
                    out.println(jump(direction));
                    break;
            }
        }

        out.close();
    }

    public static void add(char type, char direction) { rooms.add(type, direction); }
    public static int delete(char direction)          { return rooms.delete(direction).id; }
    public static int move(char direction)            { return rooms.move(direction).id; }

    public static int jump(char direction) {
        if (rooms.current.type == 'C')
            return -1; // Jika current sekarang bukan special room

        // Mencari special room terkiri di kanan current
        if (direction == 'R') {
            while (rooms.current.next.type != 'S')
                rooms.current = rooms.current.next;
            rooms.current = rooms.current.next;
        }
        // Mencari special room terkanan di kiri current
        else {
            while (rooms.current.prev.type != 'S')
                rooms.current = rooms.current.prev;
            rooms.current = rooms.current.prev;
        }

        return rooms.current.id;
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the
    // usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit
    // Exceeded caused by slow input-output (IO)
    private static class InputReader {

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

        public char nextChar() { return next().charAt(0); }
        public int nextInt()   { return Integer.parseInt(next()); }
    }
}

class DoublyLinkedList {

    private int nodeIdCounter = 1;
    ListNode first;
    ListNode current;
    ListNode last;
    int size = 0;

    /*
     * Method untuk menambahkan ListNode ke sisi kiri (prev) atau kanan (next) dari
     * {@code current} ListNode
     */
    public ListNode add(char type, char direction) {
        ListNode nodeBaru = new ListNode(type, nodeIdCounter++);
        // Jika belum ada node sama sekali
        if (first == null && current == null && last == null) {
            nodeBaru.next = nodeBaru;
            nodeBaru.prev = nodeBaru;
            first = nodeBaru;
            current = nodeBaru;
            last = nodeBaru;
        }

        // Menambahkan node disebelah kanan current
        else if (direction == 'R') {
            nodeBaru.next = current.next;
            nodeBaru.prev = current;
            current.next.prev = nodeBaru;
            current.next = nodeBaru;
        }

        // Menambahkan node disebelah kiri current
        else {
            nodeBaru.next = current;
            nodeBaru.prev = current.prev;
            current.prev.next = nodeBaru;
            current.prev = nodeBaru;
        }

        return nodeBaru;
    }

    /**
     * Method untuk menghapus ListNode di sisi kiri (prev) atau kanan (next) dari
     * {@code current} ListNode
     */
    public ListNode delete(char direction) {
        ListNode hapusNode = null;
        // Menghapus node di sebelah kanan
        if (direction == 'R') {
            hapusNode = current.next;
            current.next.next.prev = current;
            current.next = current.next.next;
        }
        // Menghapus node di sebelah kiri
        else {
            hapusNode = current.prev;
            current.prev.prev.next = current;
            current.prev = current.prev.prev;
        }

        return hapusNode;
    }

    /*
     * Method untuk berpindah ke kiri (prev) atau kanan (next) dari {@code current}
     * ListNode
     */
    public ListNode move(char direction) {
        // Memindahkan current ke kanan
        if (direction == 'R') current = current.next;
        // Memindahkan current ke kiri
        else                  current = current.prev;
        return current;
    }

    /**
     * Method untuk mengunjungi setiap ListNode pada DoublyLinkedList
     */
    public String traverse() {
        ListNode traverseNode = first;
        StringBuilder result = new StringBuilder();
        do {
            result.append(traverseNode + ((traverseNode.next != first) ? " | " : ""));
            traverseNode = traverseNode.next;
        } while (traverseNode != first);

        return result.toString();
    }
}

class ListNode {
    char type;
    ListNode next;
    ListNode prev;
    int id;

    ListNode(char type, int id) {
        this.type = type;
        this.id = id;
    }

    public String toString() { return String.format("(ID:%d Elem:%s)", id, type); }
}