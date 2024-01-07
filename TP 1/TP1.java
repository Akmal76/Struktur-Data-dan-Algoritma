/*
 * Nama         : Akmal Ramadhan
 * Kelas        : SDA-B
 * Kode Asdos   : FFF
 * Tugas Pemrograman 1 - ADT dan Rekursif
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TP1 {
    private static InputReader in;
    private static PrintWriter out;

    private static ArrayList <Wahana>      wahana       = new ArrayList<Wahana>();      // Menyimpan wahana
    private static ArrayList <Pengunjung>  pengunjung   = new ArrayList<Pengunjung>();  // Menyimpan pengunjung
    private static LinkedList <Pengunjung> daftarKeluar = new LinkedList<Pengunjung>(); // Menyimpan pengunjung di daftar keluar

    private static Knapsack[][][] memo;
    private static boolean        adaO = false;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        
        // Masukkan wahana
        int M = in.nextInt();
        for (int i = 1; i <= M; i++) wahana.add(new Wahana(i, in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt()));
        
        // Masukkan pengunjung
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) pengunjung.add(new Pengunjung(i, in.next(), in.nextInt()));

        // Masukkan query aktivitas
        int T = in.nextInt();
        while (T-- > 0) {
            String query = in.next();

            if (query.equals("A")) {    // Query A
                Pengunjung p = pengunjung.get(in.nextInt() - 1);
                Wahana     w = wahana.get(in.nextInt() - 1);

                // Kasus untuk uang pengunjung cukup untuk menaiki wahana
                if (p.getUang() >= w.getHarga()) {
                    if (p.getJenis().equals("FT")) w.getAntrianFT().add(new Pengunjung(p.getID(), p.getTotalMain()));
                    else                                    w.getAntrianR().add(new Pengunjung(p.getID(), p.getTotalMain()));
                    out.print(w.getAntrianFT().size() + w.getAntrianR().size());
                } else out.print(-1); // Jika uang nya tidak cukup atau sudah keluar dari daftar keluar
            }
            
            else if (query.equals("E")) {   // Query E
                Wahana w = wahana.get(in.nextInt() - 1);
                urutanWahana(w, null, query, w.getAntrianFT(), w.getAntrianR());
            }

            else if (query.equals("S")) {   // Query S
                Pengunjung p = pengunjung.get(in.nextInt() - 1);
                Wahana     w = wahana.get(in.nextInt() - 1);
                urutanWahana(w, p, query, new PriorityQueue<>(w.getAntrianFT()), new PriorityQueue<>(w.getAntrianR()));
            }

            else if (query.equals("F")) {   // Query F
                int P = in.nextInt();
                if      (daftarKeluar.isEmpty()) out.print(-1);
                else if (P == 0)                 out.print(daftarKeluar.pollFirst().getPoin());
                else                             out.print(daftarKeluar.pollLast().getPoin());
            }

            else if (query.equals("O")) {   // Query O
                // Precompute memoisasi untuk perintah O pertama kalinya
                if (!adaO) {
                    adaO = true;

                    int maksUang = 0;
                    for (Pengunjung p : pengunjung) if (p.getUang() > maksUang) maksUang = p.getUang(); // Cari pengunjung dengan uang paling banyak

                    memo = new Knapsack[wahana.size() + 1][maksUang + 1][3];
                    for (int i = 0; i <= wahana.size(); i++) for (int j = 0; j <= maksUang; j++) for (int k = 0; k < 3; k++) memo[i][j][k] = new Knapsack();    // Inisialisasi memoisasi

                    for (int i = wahana.size() - 1; i >= 0; i--) {  // i untuk indeks wahana
                        for (int j = 0; j <= maksUang; j++) {       // j untuk kemungkinan semua nominal uang
                            for (int k = 0; k < 3; k++) {           // parity 0 untuk genap, 1 untuk ganjil, 2 jika belom pernah mengambil
                                Wahana w = wahana.get(i);
                                // Kasus 1: skip wahana ke-i
                                memo[i][j][k].setMaksPoin(memo[i + 1][j][k].getMaksPoin());
                                // Kasus 2: naik wahana ke-i (Syarat: parity nya berbeda dan uang mencukupi)
                                if (k != i % 2 && j >= w.getHarga()) {
                                    int naikWahana = memo[i + 1][j - w.getHarga()][i % 2].getMaksPoin() + w.getPoin();
                                    if (memo[i + 1][j - w.getHarga()][i % 2].getMaksPoin() + w.getPoin() >= memo[i][j][k].getMaksPoin()) {
                                        memo[i][j][k].setMaksPoin(naikWahana);
                                        memo[i][j][k].setStatus();
                                    }
                                }
                            }
                        }
                    }
                }
                // Keluarkan maksimal poin yang dapat diperoleh
                int uang     = pengunjung.get(in.nextInt() - 1).getUang();
                int maksPoin = memo[0][uang][2].getMaksPoin();
                out.print(maksPoin + " ");

                // Mencari uang terkecil untuk maksimal poin yang diperoleh
                while (uang > 0 && memo[0][uang - 1][2].getMaksPoin() == maksPoin) uang--;

                // Mencari wahana yang dimainkan
                findWahana(0, uang, 2);
            }
            out.println();
        }

        out.close();
    }

    // Fungsi untuk perintah E dan S
    public static void urutanWahana (Wahana w, Pengunjung p, String query, PriorityQueue <Pengunjung> FT, PriorityQueue <Pengunjung> R) {
        int count = 0;      // Hitung pengunjung urutan wahana
        int sesi  = 0;      // Hitung sesi wahana

        Pengunjung tempP = null;    // Inisiasi variabel temporary pengunjung

        while (count < w.getKapasitas()) {
            // Kasus untuk tidak ada pengunjung sama sekali
            if (FT.isEmpty() && R.isEmpty()) break;

            // Selama kapasitas FT belum penuh, ambil pengunjung FT
            if  (!FT.isEmpty() && count < w.getMaksFT())    tempP = FT.poll();
            // Selama kapasitas bermain belum penuh, ambil pengunjung R
            else if (!R.isEmpty())                          tempP = R.poll();
            // Selama kapasitas FT belum penuh dan masih ada FT, ambil pengunjung FT
            else if (!FT.isEmpty())                         tempP = FT.poll();

            // Kalau uang nya ga cukup, keluarkan dari urutan
            if (pengunjung.get(tempP.getID() - 1).getUang() < w.getHarga()) continue;

            // Untuk perintah E
            if (query.equals("E")) {
                int id = tempP.getID();
                // Update total main, poin, dan uang
                pengunjung.get(id - 1).tambahTotalMain();
                pengunjung.get(id - 1).tambahPoin(w.getPoin());
                pengunjung.get(id - 1).kurangUang(w.getHarga());

                // Jika uang nya sudah habis, masukkan ke dalam daftar keluar
                if (pengunjung.get(id - 1).getUang() == 0) daftarKeluar.add(pengunjung.get(id - 1));
                
                out.print(id + " ");
            }

            // Ditemukan pengunjung yang ingin dicari urutannya (untuk perintah S)
            else if (query.equals("S") && tempP.getID() == p.getID()) { out.print((count + 1) + w.getKapasitas() * sesi); return;}

            // Update sesi (untuk perintah S)
            if (++count == w.getKapasitas() && query.equals("S")) { sesi++; count = 0; }
        }

        // Jika pengunjung tersebut tidak ada diurutan
        if (count == 0 || query.equals("S")) out.print(-1);
    }
    
    // Fungsi rekursif untuk mencari wahana yang diambil untuk perintah O
    public static void findWahana (int indeks, int uang, int parity) {
        if (indeks > wahana.size() || uang <= 0) return; // Basecase

        if (memo[indeks][uang][parity].getStatus()) {    // Wahana ke-indeks dinaiki
            out.print((indeks + 1) + " ");
            findWahana(indeks + 1, uang - wahana.get(indeks).getHarga(), indeks % 2);
            return;
        }

        findWahana(indeks + 1, uang, parity);   // Wahana ke-indeks tidak dinaiki
    }

    // Fast I/O (Input Output)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try                     { tokenizer = new StringTokenizer(reader.readLine()); }
                catch (IOException e)   { throw new RuntimeException(e); }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() { return Integer.parseInt(next()); }
    }
}

class Wahana {
    int id;
    int harga;
    int poin;
    int kapasitas;
    int maksFT;
    PriorityQueue <Pengunjung> antrianFT;
    PriorityQueue <Pengunjung> antrianR;

    public Wahana (int id, int harga, int poin, int kapasitas, int maksFT) {
        this.id        = id;
        this.harga     = harga;
        this.poin      = poin;
        this.kapasitas = kapasitas;
        this.maksFT    = (maksFT * kapasitas + 99) / 100;
        this.antrianFT = new PriorityQueue<Pengunjung>(new PengunjungComparator());
        this.antrianR  = new PriorityQueue<Pengunjung>(new PengunjungComparator());
    }

    public int getID()        { return this.id; }
    public int getHarga()     { return this.harga; }
    public int getPoin()      { return this.poin; }
    public int getKapasitas() { return this.kapasitas; }
    public int getMaksFT()    { return this.maksFT; }

    public PriorityQueue <Pengunjung> getAntrianFT() { return this.antrianFT; }
    public PriorityQueue <Pengunjung> getAntrianR()  { return this.antrianR; }
}

class Pengunjung {
    int     id;
    String  jenis;
    int     poin;
    int     uang;
    int     totalMain;

    public Pengunjung (int id, String jenis, int uang) {
        this.id        = id;
        this.jenis     = jenis;
        this.poin      = 0;
        this.uang      = uang;
        this.totalMain = 0;
    }

    public Pengunjung (int id, int totalMain) {     // Constructor untuk priority queue non-real time totalMain
        this.id        = id;
        this.totalMain = totalMain;
    }

    public int    getID()        { return this.id; }
    public String getJenis()     { return this.jenis; }
    public int    getPoin()      { return this.poin; }
    public int    getUang()      { return this.uang; }
    public int    getTotalMain() { return this.totalMain; }

    public void kurangUang(int harga)    { this.uang -= harga; }
    public void tambahPoin(int poinMain) { this.poin += poinMain; }
    public void tambahTotalMain()        { this.totalMain++; }
}

// Class comparator Pengunjung
class PengunjungComparator implements Comparator <Pengunjung> {
    public int compare (Pengunjung tp1, Pengunjung tp2) {
        if      (tp1.getTotalMain() < tp2.getTotalMain()) return -1;
        else if (tp1.getTotalMain() > tp2.getTotalMain()) return 1;
        else if (tp1.getID() < tp2.getID())               return -1;
        else                                              return 1;
    }
}

// Class Knapsack
class Knapsack {
    int     maksPoin;
    boolean ambil;

    public Knapsack () {
        this.maksPoin = 0;
        this.ambil    = false;
    }

    public int     getMaksPoin() { return this.maksPoin; }
    public boolean getStatus()   { return this.ambil; }

    public void setMaksPoin(int poin) { this.maksPoin = poin; }
    public void setStatus()           { this.ambil = true; }
}