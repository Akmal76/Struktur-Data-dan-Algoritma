public class Test {
    
    public static int mystery (int n, int m) {
        if (n == 0) return 1;
        return n * m + mystery(n/m, m);
    }
    public static void main(String[] args) {
        System.out.println(mystery(20, 2));
    }
}