/*
 * Nama         : Akmal Ramadhan
 * Kelas        : SDA-B
 * Kode Asdos   : FFF
 * Tugas Pemrograman 3 - Binary Heap dan Graph
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

public class TP3 {
    public static void main(String[] args) {
        InputStream  inputStream  = System.in;
        OutputStream outputStream = System.out;
        InputReader  in  = new InputReader(inputStream);
        PrintWriter  out = new PrintWriter(outputStream);

        int  v    = in.nextInt();       // Jumlah vertex
        int  e    = in.nextInt();       // Jumlah edge
        Graf graf = new Graf(v);        // Buat graf dengan v vertex dan e edge
        
        ArrayList<Integer> treasureRooms = new ArrayList<Integer>();
        for (int i = 1; i <= v; i++) if (in.next().equals("S")) treasureRooms.add(i);  // Simpan semua treasure rooms
        for (int i = 1; i <= e; i++) graf.addEdge(in.nextInt(), in.nextInt(), in.nextLong());   // Tambahkan semua edge ke graf

        long[][] maxEdges = new long[v + 1][v + 1];     // Lakukan Dijsktra untuk tiap treasure room
        for (int i : treasureRooms) {
            long[] dist = graf.dijkstraForMS(i);
            for (int j = 1; j <= v; j++) maxEdges[i][j] = dist[j];
        }

        int q = in.nextInt();
        while (q --> 0) {
            String perintah = in.next();
            if (perintah.equals("M")) {         // Query M: Mencari jumlah treasure rooms yang bisa dikunjungi dengan group size tertentu
                long groupSize = in.nextLong();
                int  count     = 0;
                for (int i : treasureRooms) if (maxEdges[i][1] <= groupSize) count++;
                out.println(count);
            } else if (perintah.equals("S")) {  // Query S: Mencari group size minimum untuk mengunjungi treasure room dari room tertentu
                int  startID = in.nextInt();
                long ans     = Long.MAX_VALUE;
                for (int i : treasureRooms) ans = Math.min(ans, maxEdges[i][startID]);
                out.println(ans);
            } else if (perintah.equals("T")) {  // Query T: Mencari apakah bisa melewati room A ke room C melewati room B dengan group size tertentu
                // Kompleksitas: 
                int startID    = in.nextInt();
                int middleID   = in.nextInt();
                int endID      = in.nextInt();
                long groupSize = in.nextLong();
                if (graf.dfsForT(startID, middleID, groupSize, new boolean[v + 1])) {
                    if   (graf.dfsForT(middleID, endID, groupSize, new boolean[v + 1])) out.println("Y");
                    else                                                                out.println("H");
                } else out.println("N");
            }
        }

        out.close();
    }

    static class InputReader { // Fast Input / Output
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

        public int  nextInt()  { return Integer.parseInt(next()); }
        public long nextLong() { return Long.parseLong(next()); }
    }
}

class Graf { // Kelas untuk graf
    public int v;                              // Jumlah vertex
    public ArrayList<ArrayList<Edge>> adj;     // Adjacency list

    public Graf(int v) { // Konstruktor graf
        this.v   = v;
        this.adj = new ArrayList<>();
        for (int i = 0; i <= v; i++) this.adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, long w) {                // Menambahkan edge ke graf
        this.adj.get(u).add(new Edge(v, w));
        this.adj.get(v).add(new Edge(u, w));
    }

    public long[] dijkstraForMS(int startVertex) {             // Dijkstra untuk Query M dan S
        long[] dist = new long[this.v + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[startVertex] = 0;

        BinaryHeap pq = new BinaryHeap(this.v);                 // Buat Binary Heap untuk menyimpan pasangan (vertex, bobot)
        pq.add(new Pair(startVertex, dist[startVertex]));       // Masukkan pasangan (startVertex, 0) ke Binary Heap

        while (!pq.isEmpty()) {                                 // Lakukan Dijkstra 
            Pair curr = pq.poll();
            for (Edge e : this.adj.get(curr.from)) {     
                if (dist[e.to] > Math.max(curr.weight, e.weight)) {
                    dist[e.to] = Math.max(curr.weight, e.weight);
                    pq.add(new Pair(e.to, dist[e.to]));
                }
            }
        }
        return dist;
    }

    public boolean dfsForT(int startVertex, int endVertex, long groupSize, boolean[] visited) { // DFS untuk Query T
        visited[startVertex] = true;                    
        if (startVertex == endVertex) return true;                                                                                                          // Jika sudah sampai di endVertex, return true
        for (Edge e : this.adj.get(startVertex)) if (!visited[e.to] && e.weight <= groupSize && dfsForT(e.to, endVertex, groupSize, visited)) return true;  // Jika belum sampai di endVertex, cek semua vertex yang terhubung dengan startVertex
        return false;                                                                                                                                       // Jika tidak ada yang terhubung dengan startVertex, return false
    }
}

class Edge {                                   // Kelas untuk edge
    public int  to;
    public long weight;

    public Edge(int to, long weight) {
        this.to     = to;
        this.weight = weight;
    }
}

class Pair implements Comparable<Pair> {       // Kelas untuk pasangan Edge
    public int  from;
    public long weight;

    public Pair(int from, long weight) {
        this.from   = from;
        this.weight = weight;
    }

    public int compareTo(Pair o) { return (int) (this.weight - o.weight); }
}

class BinaryHeap {                               // Kelas untuk Binary Heap (Min Heap)
    public int    capacity;
    public int    size;
    public Pair[] data;

    public BinaryHeap(int capacity) {           // Konstruktor Binary Heap
        this.capacity = capacity;
        this.data     = new Pair[capacity];
    }

    // Fungsi getter untuk mengakses data parent, left child, dan right child dari suatu node
    public boolean isEmpty() { return this.size == 0; }
    public boolean hasParent(int index) { return indexParent(index) >= 0; }
    public boolean hasLeft(int index)   { return indexLeft(index) < size; }
    public boolean hasRight(int index)  { return indexRight(index) < size; }
    public Pair parent(int index) { return data[indexParent(index)]; }
    public Pair left(int index)   { return data[indexLeft(index)]; }
    public Pair right(int index)  { return data[indexRight(index)]; }
    public int indexParent(int childIndex) { return (childIndex - 1) / 2; }
    public int indexLeft(int parentIndex)  { return 2 * parentIndex + 1; }
    public int indexRight(int parentIndex) { return 2 * parentIndex + 2; }

    public void add(Pair pair) {                            // Fungsi untuk menambah node baru
        if (size == capacity && capacity == 0) {            // Jika kapasitas heap masih 0, maka buat array baru dengan kapasitas 1
            data = Arrays.copyOf(data, 1);
            capacity = 1;
        } else if (size == capacity && capacity > 0) {      // Jika kapasitas heap sudah penuh, maka buat array baru dengan kapasitas 2 kali lipat
            data = Arrays.copyOf(data, capacity * 2);
            capacity *= 2;
        }
        data[size++] = pair;
        int index    = size - 1;                                                       // Pastikan bahwa node yang ditambahkan berada di posisi yang tepat (Percolate Up)
        while (hasParent(index) && parent(index).compareTo(data[index]) > 0) {
            swap(indexParent(index), index);
            index = indexParent(index);
        }
    }

    public Pair poll() {                    // Fungsi untuk menghapus node dengan bobot terkecil
        if (size == 0) return null;
        Pair pair = data[0];
        data[0]   = data[--size];
        int index = 0;                                                              // Pastikan bahwa node yang dihapus berada di posisi yang tepat (Percolate Down)
        while (hasLeft(index)) {
            int indexChildTerkecil = indexLeft(index);
            if (hasRight(index) && right(index).compareTo(left(index)) < 0) indexChildTerkecil = indexRight(index);
            if (data[index].compareTo(data[indexChildTerkecil]) < 0) break;
            else swap(index, indexChildTerkecil);
            index = indexChildTerkecil;
        }
        return pair;
    }

    public void swap(int a, int b) {        // Fungsi untuk menukar posisi 2 node
        Pair temp = data[a];
        data[a]   = data[b];
        data[b]   = temp;
    }
}