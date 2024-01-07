/*
 * Nama         : Akmal Ramadhan
 * Kelas        : SDA-B
 * Kode Asdos   : FFF
 * Tugas Pemrograman 2 - Sorting, Linked List, dan Tree
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class TP2 {
    private static InputReader       in;
    private static PrintWriter       out;
    private static DoubleLinkedList  SMPL        = new DoubleLinkedList();
    public  static ArrayList <Siswa> daftarSiswa = new ArrayList<Siswa>();
    public  static int               idKelas     = 1;

    public static void main(String[] args) {
        InputStream  inputStream  = System.in;
        OutputStream outputStream = System.out;
        in  = new InputReader(inputStream);
        out = new PrintWriter(outputStream);

        int   M = in.nextInt();                                                     // Menerima masukkan banyak kelas
        int[] Mi = new int[M+1];                        
       
        for (int i = 1; i <= M; i++) Mi[i] = in.nextInt();                          // Menerima masukkan banyak siswa pada kelas ke-i
        for (int i = 1; i <= M; i++) {
            AVLTree pohonPoin = new AVLTree();                                      // Membuat AVL Tree untuk kelas ke-i
            long sum = 0;
            for (int j = 1; j <= Mi[i]; j++) {
                int poin  = in.nextInt();                                           // Menerima masukkan poin siswa ke-j pada kelas ke-i
                
                Siswa siswa = new Siswa(daftarSiswa.size() + 1, poin, 0);                    // Membuat siswa baru
                daftarSiswa.add(siswa);                                                             // Menambahkan siswa baru ke dalam daftar siswa
                
                pohonPoin.root  = pohonPoin.insert(pohonPoin.root, siswa.id, poin, siswa.curang);   // Menambahkan siswa baru ke dalam AVL Tree
                sum            += (long) poin;                                                      // Menambahkan poin siswa ke-j ke dalam sum
            }
            SMPL.tambahKelas(idKelas++, sum, pohonPoin);                            // Menambahkan kelas ke-i ke dalam Double Linked List
        }

        int Q = in.nextInt();              // Menerima masukkan banyak query
        while (Q --> 0) {
            String perintah = in.next();   // Menerima masukkan perintah (T, C, G, S, K, A)
            Kelas kelas = SMPL.current;

            if (perintah.equals("T")) {                                                                 // Perintah T (Kompleksitas O(log N)
                int poin = in.nextInt();
                int id   = in.nextInt();

                if (id > daftarSiswa.size()) { out.println(-1); continue; }                                     // Kasus jika id siswa > banyaknya siswa dalam satu sekolah

                Siswa siswa = daftarSiswa.get(id - 1);
                if (kelas.siswa.find(siswa.poin, id) == null) { out.println(-1); continue; }                    // Kasus jika siswa tidak ada di kelas sekarang

                kelas.sum        -= siswa.poin;
                kelas.siswa.root  = kelas.siswa.delete(kelas.siswa.root, siswa.id, siswa.poin);                 // Menghapus siswa dari kelas

                int tutor = kelas.siswa.banyakTutor(kelas.siswa.root, siswa.poin);                              // Mencari banyak siswa yang memiliki poin <= siswa yang diberi tugas
                siswa.poin += (tutor < poin ? poin + tutor : poin * 2);                                         // Menambahkan poin siswa
                
                kelas.siswa.root  = kelas.siswa.insert(kelas.siswa.root, siswa.id, siswa.poin, siswa.curang);   // Menambahkan siswa ke dalam kelas
                kelas.sum        += siswa.poin;
                
                out.println(siswa.poin);
            }

            else if (perintah.equals("C")) {                                                                                       // Perintah C (muncul ketika kelas > 1) (Kompleksitas O(log N)
                int id = in.nextInt();
                
                if (id > daftarSiswa.size()) { out.println(-1); continue; }                                                                 // Kasus jika id siswa > banyaknya siswa dalam satu sekolah
                Siswa siswa = daftarSiswa.get(id - 1);
                if (kelas.siswa.find(siswa.poin, id) == null) { out.println(-1); continue; }                                                // Kasus jika siswa tidak ada di kelas sekarang

                kelas.sum        -= siswa.poin;
                kelas.siswa.root  = kelas.siswa.delete(kelas.siswa.root, siswa.id, siswa.poin);                                             // Menghapus siswa dari kelas sekarang
                siswa.poin        = 0;
                
                if (++siswa.curang == 1) {                                                                                                  // Siswa curang untuk pertama kalinya
                    kelas.siswa.root = kelas.siswa.insert(kelas.siswa.root, siswa.id, siswa.poin, siswa.curang);
                    out.println(0); 
                } else {
                    if (siswa.curang == 2) {                                                                                                              // Siswa curang untuk kedua kalinya
                        if  (kelas != SMPL.last) SMPL.last.siswa.root = SMPL.last.siswa.insert(SMPL.last.siswa.root, siswa.id, siswa.poin, siswa.curang); // Siswa curang di kelas bukan terakhir, pindahkan ke kelas yang lebih buruk
                        else                     kelas.siswa.root     = kelas.siswa.insert(kelas.siswa.root, siswa.id, siswa.poin, siswa.curang);
                    }                                                                                 
                    if (kelas.siswa.root.sizes < 6) {                                                                                       // Kasus jika kelas memiliki siswa kurang dari 6
                        if (kelas == SMPL.last) {                                                                                           // Jika sekarang kelas terakhir, pindahkan isi AVL Tree dari kanan ke kiri
                            AVLTree kiri = kelas.prev.siswa;
                            while (kelas.siswa.root != null) {
                                kiri.root        = kiri.insert(kiri.root, kelas.siswa.root.id, kelas.siswa.root.poin, kelas.siswa.root.curang);
                                kelas.siswa.root = kelas.siswa.delete(kelas.siswa.root, kelas.siswa.root.id, kelas.siswa.root.poin);
                            }
                            kelas.prev.sum += kelas.sum;
                            SMPL.pindahKelas("L");
                            SMPL.hapusKelas("R");                                                                                      
                        } else {                                                                                                            // Jika tidak, pindahkan isi AVL Tree dari kiri ke kanan
                            AVLTree kanan = kelas.next.siswa;
                            while (kelas.siswa.root != null) {
                                kanan.root       = kanan.insert(kanan.root, kelas.siswa.root.id, kelas.siswa.root.poin, kelas.siswa.root.curang);
                                kelas.siswa.root = kelas.siswa.delete(kelas.siswa.root, kelas.siswa.root.id, kelas.siswa.root.poin);
                            }
                            kelas.next.sum += kelas.sum;
                            SMPL.pindahKelas("R");
                            SMPL.hapusKelas("L");                                                                                     
                        }
                    }
                    if      (siswa.curang == 2) out.println(SMPL.last.id);                                                                  // Output untuk siswa curang untuk kedua kalinya
                    else if (siswa.curang == 3) out.println(siswa.id);                                                                      // Output untuk siswa curang untuk ketiga kalinya
                }
            }

            else if (perintah.equals("G")) out.println(SMPL.pindahKelas(in.next()).id);         // Perintah G (Kompleksitas O(1)

            else if (perintah.equals("S")) {                                                    // Perintah S (Kompleksitas O(log N)
                AVLTree poinSiswa = kelas.siswa;
                if      (kelas.prev == kelas && kelas == kelas.next) { out.println("-1 -1"); continue; }  // Jika sekolah hanya memiliki satu kelas
                else if (kelas == SMPL.last)  tukarDuaKelas(kelas.prev, kelas);                             // Jika kelas sekarang berada di ujung kanan
                else if (kelas == SMPL.first) tukarDuaKelas(kelas, kelas.next);                             // Jika kelas sekarang berada di ujung kiri
                else {                                                                                      // Jika kelas memiliki tetangga kiri dan kanan
                    Stack <Siswa> pintarTengah = new Stack<Siswa>();
                    Stack <Siswa> pintarKanan  = new Stack<Siswa>();
                    Stack <Siswa> bodohKiri    = new Stack<Siswa>();
                    Stack <Siswa> bodohTengah  = new Stack<Siswa>();

                    Kelas kiri  = kelas.prev;
                    Kelas kanan = kelas.next;

                    masukkanSiswaKeStack(bodohKiri, kiri, kelas, false);                          
                    masukkanSiswaKeStack(bodohTengah, kelas, kanan, false);
                    masukkanSiswaKeStack(pintarTengah, kelas, kiri, true);
                    masukkanSiswaKeStack(pintarKanan, kanan, kelas, true);

                    masukkanSiswaKeTree(bodohKiri, kelas.siswa);
                    masukkanSiswaKeTree(bodohTengah, kanan.siswa);
                    masukkanSiswaKeTree(pintarTengah, kiri.siswa);
                    masukkanSiswaKeTree(pintarKanan, kelas.siswa);
                }
                out.println(poinSiswa.maxValueSiswa(poinSiswa.root).id + " " + poinSiswa.minValueSiswa(poinSiswa.root).id);
            }

            else if (perintah.equals("K")) {                        // Perintah K (Kompleksitas O(N log N)
                DoubleLinkedList  temp        = new DoubleLinkedList();
                ArrayList <Kelas> daftarKelas = new ArrayList<Kelas>();
                Kelas             currKelas   = SMPL.first;                  // Salin isi Double Linked List ke dalam Array List
                do {
                    daftarKelas.add(currKelas);
                    currKelas = currKelas.next;
                } while (currKelas != SMPL.first);

                Kelas sekarang = SMPL.current;                              // Simpan kelas sekarang
                mergeSort(daftarKelas, 0, daftarKelas.size()-1);          // Lakukan merge sort terhadap Array List

                int count = 0;
                for (Kelas k : daftarKelas) {                               // Salin isi Array List ke dalam Double Linked List
                    count++;
                    temp.tambahKelas(k.id, k.sum, k.siswa);
                    if (k.id == sekarang.id) {                              // Jika kelas sekarang sudah ditemukan, keluarkan urutan kelas sekarang
                        temp.current = temp.last;
                        out.println(count);
                    }
                }

                SMPL = temp;
            }

            else if (perintah.equals("A")) {                        // Perintah A (Kompleksitas O(log N)
                int N = in.nextInt();
                AVLTree kelasBaru = new AVLTree();    
                for (int i = 0; i < N; i++) {
                    Siswa siswa           = new Siswa(daftarSiswa.size() + 1, 0, 0);
                    daftarSiswa.add(siswa);
                    
                    kelasBaru.root  = kelasBaru.insert(kelasBaru.root, siswa.id, 0, siswa.curang);
                }
                out.println((SMPL.tambahKelas(idKelas++, 0, kelasBaru)).id);
            }
        }

        out.close();
    }
    
    public static void tukarDuaKelas (Kelas kelasA, Kelas kelasB) {                                             // Fungsi untuk menukar tiga siswa terbaik/terburuk dengan rata-rata A >= B untuk query S
        Stack <Siswa> pintar = new Stack<Siswa>();
        Stack <Siswa> bodoh  = new Stack<Siswa>();

        masukkanSiswaKeStack(bodoh, kelasA, kelasB, false);
        masukkanSiswaKeStack(pintar, kelasB, kelasA, true);

        masukkanSiswaKeTree(pintar, kelasA.siswa);
        masukkanSiswaKeTree(bodoh, kelasB.siswa);
    }

    public static void masukkanSiswaKeStack (Stack <Siswa> siswa, Kelas kelasA, Kelas kelasB, boolean isMax) {  // Fungsi untuk memasukkan tiga siswa terbaik/terburuk dari kelas A ke kelas B menggunakan dalam stack untuk query S
        Siswa temp;
        for (int i = 1; i <= 3; i++) {
            if (isMax) temp   = kelasA.siswa.maxValueSiswa(kelasA.siswa.root);                                  // Jika isMax == true, ambil siswa dengan poin terbesar
            else       temp   = kelasA.siswa.minValueSiswa(kelasA.siswa.root);                                  // Jika isMax == false, ambil siswa dengan poin terkecil
            kelasA.siswa.root = kelasA.siswa.delete(kelasA.siswa.root, temp.id, temp.poin);
            kelasA.sum       -= temp.poin;
            kelasB.sum       += temp.poin;
            siswa.push(temp);
        }
    }

    public static void masukkanSiswaKeTree (Stack <Siswa> siswa, AVLTree kelas) {                               // Fungsi untuk memasukkan tiga siswa terbaik/terburuk ke dalam AVL Tree untuk query S
        while (!siswa.isEmpty()) {
            Siswa s    = siswa.pop();
            kelas.root = kelas.insert(kelas.root, s.id, s.poin, s.curang);
        }
    }

    public static void mergeSort(ArrayList<Kelas> arr, int l, int r) {      // Merge Sort untuk Array List untuk query K   
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);                                           // Sort bagian kiri
            mergeSort(arr, m+1, r);                                         // Sort bagian kanan
            merge(arr, l, m, r);                                            // Merge dua bagian
        }
    }

    public static void merge(ArrayList<Kelas> arr, int l, int m, int r) {                   // Merge dua bagian
        int n1 = m - l + 1;                                                                 // Jumlah elemen bagian kiri
        int n2 = r - m;                                                                     // Jumlah elemen bagian kanan

        ArrayList<Kelas> L = new ArrayList<Kelas>();
        ArrayList<Kelas> R = new ArrayList<Kelas>();

        for (int i = 0; i < n1; i++) L.add(arr.get(l + i));                                 // Salin elemen bagian kiri ke ArrayList L
        for (int j = 0; j < n2; j++) R.add(arr.get(m + 1 + j));                             // Salin elemen bagian kanan ke ArrayList R

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            Kelas kiri  = L.get(i);                                                         
            Kelas kanan = R.get(j);
            /*
             * Membandingkan rata-rata kelas kiri dan kanan
             * kiri.sum / kiri.siswa.root.sizes     >       kanan.sum / kanan.siswa.root.sizes
             * kiri.sum * kanan.siswa.root.sizes    >       kanan.sum * kiri.siswa.root.sizes     (lakukan perkalian silang agar tidak terjadi pembagian)
             */
            long a = ((kanan.siswa.root == null) ? 0 : kanan.siswa.root.sizes) * kiri.sum;  
            long b = ((kiri.siswa.root  == null) ? 0 : kiri.siswa.root.sizes)  * kanan.sum;
            if      (a > b)              arr.set(k++, L.get(i++));                          // Jika rata-rata kiri > kanan, salin elemen kiri ke array utama
            else if (a < b)              arr.set(k++, R.get(j++));                          // Jika rata-rata kiri < kanan, salin elemen kanan ke array utama
            else if (kiri.id < kanan.id) arr.set(k++, L.get(i++));                          // Jika rata-rata kiri == kanan dan kiri.id < kanan.id, salin elemen kiri ke array utama
            else                         arr.set(k++, R.get(j++));                          // Jika rata-rata kiri == kanan dan kiri.id > kanan.id, salin elemen kanan ke array utama
        }
        while (i < n1) arr.set(k++, L.get(i++));                                            // Salin elemen kiri yang belum terisi ke array utama
        while (j < n2) arr.set(k++, R.get(j++));                                            // Salin elemen kanan yang belum terisi ke array utama
    }

    static class InputReader {                                                                      // Fast I/O (Input Output)
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader    = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try                   { tokenizer = new StringTokenizer(reader.readLine()); }
                catch (IOException e) { throw new RuntimeException(e); }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() { return Integer.parseInt(next()); }
    }
}

class Kelas {                                           // Kelas merupakan Double Linked List Siswa                                        
    int     id;
    long    sum;
    Kelas   prev, next;
    AVLTree siswa;

    public Kelas (int id, long sum, AVLTree siswa) {
        this.id             = id;
        this.sum            = sum;
        this.siswa          = siswa;
    }
}

class DoubleLinkedList {                                                // Double Linked List
    int   id;
    Kelas first, current, last;
    
    public Kelas tambahKelas (int id, long sum, AVLTree kelas) {        // Fungsi untuk menambahkan kelas baru ke dalam Double Linked List
        Kelas kelasBaru = new Kelas(id, sum, kelas);
        if (first == null && current == null && last == null) {         // Jika belum ada kelas, tambahkan kelas di paling kiri
            kelasBaru.prev = kelasBaru;
            kelasBaru.next = kelasBaru;
            first          = kelasBaru;
            current        = kelasBaru;
        } else {                                                        // Jika sudah ada kelas, tambahkan kelas di paling kanan
            kelasBaru.next = last.next;
            kelasBaru.prev = last;
            last.next.prev = kelasBaru;
            last.next      = kelasBaru;
        }
        last           = kelasBaru;
        return kelasBaru;
    }
    
    public Kelas pindahKelas (String arah) { return (arah.equals("R")) ? (current = current.next) : (current = current.prev); } // Fungsi untuk pindah kelas

    public Kelas hapusKelas (String arah) {                             // Fungsi untuk menghapus kelas
        Kelas hapus = null;
        if (arah.equals("R")) {                                // Jika arah == R, hapus kelas di sebelah kanan
            hapus = current.next;
            if      (hapus == first) first = current.next.next;         // Jika kelas yang dihapus adalah kelas pertama, pindahkan first ke kelas selanjutnya
            else if (hapus == last)  last  = current;                   // Jika kelas yang dihapus adalah kelas terakhir, pindahkan last ke kelas sekarang
            current.next.next.prev = current;                           
            current.next           = current.next.next;
        } else {
            hapus = current.prev;                                       // Jika arah == L, hapus kelas di sebelah kiri
            if      (hapus == first) first = current;                   // Jika kelas yang dihapus adalah kelas pertama, pindahkan first ke kelas sekarang
            else if (hapus == last)  last  = current.prev.prev;         // Jika kelas yang dihapus adalah kelas terakhir, pindahkan last ke kelas sebelumnya
            current.prev.prev.next = current;
            current.prev           = current.prev.prev;
        }
        return hapus;
    }
}

class Siswa {                               // Siswa merupakan AVL Tree Node                                       
    int id, poin, curang, height, sizes;
    Siswa left, right;

    public Siswa (int id, int poin, int curang) {
        this.id     = id;
        this.poin   = poin;
        this.curang = curang;
        this.height = 1;
        this.sizes  = 1;
    }
}

class AVLTree {                                                                                 // AVL Tree
    Siswa root;

    Siswa insert(Siswa node, int id, int poin, int curang) {                                    // Fungsi untuk menambahkan siswa ke dalam AVL Tree
        if      (node == null) return (new Siswa(id, poin, curang)); 
        if      (poin < node.poin || (poin == node.poin && id > node.id)) node.left  = insert(node.left, id, poin, curang);
        else if (poin > node.poin || (poin == node.poin && id < node.id)) node.right = insert(node.right, id, poin, curang);
        else                                                              return node;
        updateHeightAndSize(node, null);
        return balancing(node);
    }

    Siswa delete(Siswa node, int id, int poin) {                                                // Fungsi untuk menghapus siswa dari AVL Tree
        if      (node == null) return node;
        if      (poin < node.poin || (poin == node.poin && id > node.id)) node.left  = delete(node.left, id, poin);
        else if (poin > node.poin || (poin == node.poin && id < node.id)) node.right = delete(node.right, id, poin);
        else {
            if (node.left == null || node.right == null) {                                      // Kasus jika node memiliki satu atau nol child
                Siswa temp;
                if (node.left != null) temp = node.left;                                        // Cari child dari node
                else                   temp = node.right;                                               
                if (temp == null) {                                                             // Kasus jika node tidak memiliki child
                    temp = node;
                    node = null;
                } 
                else node = temp;                                                               // Kasus jika node memiliki satu child
            } else {                                                                            // Kasus jika node memiliki dua child
                Siswa temp = maxValueSiswa(node.left);                                          // Cari node dengan poin terbesar dari subtree kiri (predecessor)
                node.poin  = temp.poin;
                node.id    = temp.id;
                node.left  = delete(node.left, temp.id, temp.poin);                             // Hapus node dengan poin terbesar dari subtree kiri
            }
        }

        if (node == null) return null;
        updateHeightAndSize(node, null);
        return balancing(node);
    }

    Siswa balancing(Siswa node) {                                                           // Fungsi untuk menyeimbangkan AVL Tree
        if (node == null) return null;
        int balance = balance(node);
        if ((balance > 1) && (balance(node.left) >= 0)) return singleRightRotate(node);     // Kasus Left Left
        if ((balance > 1) && (balance(node.left) < 0)) {                                    // Kasus Left Right
            node.left = singleLeftRotate(node.left);
            return singleRightRotate(node);
        }
        if ((balance < -1) && (balance(node.right) <= 0)) return singleLeftRotate(node);    // Kasus Right Right
        if ((balance < -1) && (balance(node.right) > 0)) {                                  // Kasus Right Left
            node.right = singleRightRotate(node.right);
            return singleLeftRotate(node);
        }
        return node;
    }

    Siswa singleLeftRotate(Siswa node) {                                                    // Fungsi untuk melakukan single left rotate
        Siswa newSiswa = node.right;
        node.right     = newSiswa.left;
        newSiswa.left  = node;
        return updateHeightAndSize(node, newSiswa);
    }

    Siswa singleRightRotate(Siswa node) {                                                   // Fungsi untuk melakukan single right rotate
        Siswa newSiswa = node.left;
        node.left      = newSiswa.right;
        newSiswa.right = node;
        return updateHeightAndSize(node, newSiswa);
    }

    Siswa updateHeightAndSize (Siswa node, Siswa newSiswa) {                                                    // Fungsi untuk mengupdate tinggi dan child dari AVL Tree
        node.height = Math.max(height(node.left), height(node.right)) + 1;                                      // Update tinggi node
        if (newSiswa != null) newSiswa.height = Math.max(height(newSiswa.left), height(newSiswa.right)) + 1;
        node.sizes = 1 + getSize(node.left) + getSize(node.right);                                              // Update child node
        if (newSiswa != null) newSiswa.sizes = 1 + getSize(newSiswa.left) + getSize(newSiswa.right); 
        return newSiswa;
    }
    
    Siswa maxValueSiswa (Siswa node) {                                      // Fungsi untuk mencari siswa dengan poin terbesar
        while (node.right != null) node = node.right;
        return node;
    }

    Siswa minValueSiswa (Siswa node) {                                      // Fungsi untuk mencari siswa dengan poin terkecil
        while (node.left != null) node = node.left;
        return node;
    }

    Siswa find (int poin, int id) {                                         // Fungsi untuk mencari siswa dengan poin dan id tertentu
        Siswa node = root;
        while (node != null) {
            if      (node.poin == poin && node.id == id) return node;
            if      (poin < node.poin || (poin == node.poin && id > node.id)) node = node.left;
            else if (poin > node.poin || (poin == node.poin && id < node.id)) node = node.right;
        }
        return null;
    }

    int height (Siswa node) { return (node == null) ? 0 : node.height;}                             // Fungsi untuk mendapatkan tinggi node
    int balance(Siswa node) { return (node == null) ? 0 : height(node.left) - height(node.right);}  // Fungsi untuk mendapatkan balance factor node
    int getSize(Siswa node) { return (node == null) ? 0 : node.sizes;}                              // Fungsi untuk mendapatkan banyak child node

    int banyakTutor (Siswa node, int poin) {                                // Fungsi untuk mencari banyak siswa yang memiliki poin <= siswa yang diberi tugas
        if (node == null)     return 0;
        if (node.poin > poin) return banyakTutor(node.left, poin);
        return banyakTutor(node.right, poin) + getSize(node.left) + 1;
    }
}