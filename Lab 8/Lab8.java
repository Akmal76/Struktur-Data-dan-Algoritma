import java.io.*;
import java.util.*;

public class Lab8 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N = in.nextInt();
        int E = in.nextInt();
        Graph graph = new Graph(N);

        // TODO: Bangun graf
        for (int i = 0; i < E; i++) {
            int A = in.nextInt();
            int B = in.nextInt();
            long W = in.nextLong();
            // TODO: Inisiasi jalur antara A dan B
            graph.addEdge(A, B, W);
            graph.addEdge(B, A, W);
        }

        int H = in.nextInt(); 
        ArrayList<Integer> treasureNodes = new ArrayList<Integer>();
        for (int i = 0; i < H; i++) {
            int K = in.nextInt();
            // TODO: Simpan titik yang memiliki harta karun
            treasureNodes.add(K);
        }
        treasureNodes.add(1);

        TreeMap <Integer, ArrayList<Long>> minDist = new TreeMap<>();
        for (int treasure : treasureNodes) {
            ArrayList<Long> dist = graph.dijkstra(treasure);
            minDist.put(treasure, dist);
            for (int i = 1; i < dist.size(); i++) if (dist.get(i) < minDist.get(treasure).get(i)) minDist.get(treasure).set(i, dist.get(i));
        }

        int Q = in.nextInt();
        int O = in.nextInt();
        while (Q-- > 0) {
            Long totalOxygenNeeded = (long) 0;

            int T = in.nextInt();
            int davePosition = 1;
            while (T-- > 0) {
                int D = in.nextInt();
                // TODO: Update total oksigen dibutuhkan
                totalOxygenNeeded += minDist.get(davePosition).get(D);
                // TODO: Update posisi Dave
                davePosition = D;
            }
            // TODO: Implementasi Dave kembali ke daratan
            totalOxygenNeeded += minDist.get(davePosition).get(1);

            // TODO: Cetak 0 (rute tidak aman) atau 1 (rute aman)
            if (O-totalOxygenNeeded >= 1) out.println(1);
            else out.println(0);
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
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt()   { return Integer.parseInt(next()); }
        public long nextLong() { return Long.parseLong(next()); }
    }
}

// TODO: Implementasi Graph
class Graph {
    public int V;
    public ArrayList<ArrayDeque<Edge>> adj;
    public ArrayList<Long> dist;

    public Graph(int v) {
        this.V = v;
        this.adj = new ArrayList<>();
        for (int i = 0; i <= v; i++) this.adj.add(new ArrayDeque<>());
    }
    
    // TODO: Implementasi tambahkan edge ke graph
    public void addEdge(int v, int w, long weight) { this.adj.get(v).add(new Edge(w, weight)); }

    // TODO: Implementasi shortest path (bebas mengubah fungsi ini)
    public ArrayList<Long> dijkstra(int source) {
        if (source == 0) return null;
        ArrayList<Long> dist = new ArrayList<>();
        for (int i = 0; i <= this.V; i++) dist.add(Long.MAX_VALUE);
        dist.set(source, (long) 0);

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(source, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int v = curr.src;     // source
            long w = curr.oxygen; // oksigen

            if (w > dist.get(v)) continue;

            for (Edge e : this.adj.get(v)) {
                int u = e.to;
                long weight = e.weight;
                if (dist.get(v) + weight < dist.get(u)) {
                    dist.set(u, dist.get(v) + weight);
                    pq.add(new Pair(u, dist.get(u)));
                }
            }
        }

        return dist;
    }
}

// Class Edge
class Edge {
    int to;
    long weight;

    public Edge(int to, long weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.to + " " + this.weight;
    }
}

// Class Pair
class Pair implements Comparable<Pair> {
    int src;
    long oxygen;

    public Pair(int src, long oxygen) {
        this.src = src;
        this.oxygen = oxygen;
    }

    @Override
    public int compareTo(Pair o) {
        return (int) (this.oxygen - o.oxygen);
    }
}