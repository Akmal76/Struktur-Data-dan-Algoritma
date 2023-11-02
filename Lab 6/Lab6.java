import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Lab6 {
    private static InputReader in;
    public static PrintWriter out;
    static AVLTree tree = new AVLTree();
    static TreeSet <Integer> apel = new TreeSet<Integer>();

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int apple = in.nextInt();
            tree.root = tree.insert(tree.root, apple);
            apel.add(apple);
        }

        int Q = in.nextInt();
        for (int i = 0; i < Q; i++) {
            char query = in.nextChar();

            if      (query == 'G')  { grow(in.nextInt()); }
            else if (query == 'P')  { pick(in.nextInt()); }
            else if (query == 'F')  { fall(); }
            else                    { height(); }
        }

        out.close();
    }

    static void grow(int apple) {
        tree.root = tree.insert(tree.root, apple);
        apel.add(apple);
    }

    static void pick(int apple) {
        if (tree.root == null || apel == null || !apel.contains(apple)) {
            out.println(-1);
            return;
        }
        apel.remove(apple);
        tree.root = tree.delete(tree.root, apple);
        out.println(apple);
    }

    static void fall() {
        
        if (tree.root == null) {
            out.println(-1);
            return;
        }
        
        Node apple = tree.maxi(tree.root);

        if (apel == null || !apel.contains(apple.key)) {
            out.println(-1);
            return;
        }

        apel.remove(apple.key);
        tree.root = tree.delete(tree.root, apple.key);
        out.println(apple.key);
    }

    static void height() {
        if (tree.root == null) out.println(0);
        else out.println(tree.root.height);
    }

    // taken from https://www.programiz.com/dsa/avl-tree
    // a method to print the contents of a Tree data structure in a readable
    // format. it is encouraged to use this method for debugging purposes.
    // to use, simply copy and paste this line of code:
    // printTree(tree.root, "", true);
    static void printTree(Node currPtr, String indent, boolean last) {
        if (currPtr != null) {
            out.print(indent);
            if (last) {
                out.print("R----");
                indent += "   ";
            } else {
                out.print("L----");
                indent += "|  ";
            }
            out.println(currPtr.key);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the
    // usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit
    // Exceeded caused by slow input-output (IO)
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

        public char nextChar() {
            return next().charAt(0);
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key    = key;
        this.height = 1;
    }
}

class AVLTree {
    Node root;

    Node insert(Node node, int key) {
        if (node == null) return (new Node(key));

        if      (key < node.key) node.left  = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else                     return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return balancing(node);
    }

    Node delete(Node node, int key) {
        if (node == null) return node;

        if (key < node.key)      node.left = delete(node.left, key);
        else if (key > node.key) node.right = delete(node.right, key);
        else {
            // satu atau nol child
            if (node.left == null || node.right == null) {
                Node temp;
                if (node.left != null) temp = node.left;
                else                   temp = node.right;

                if (temp == null) {
                    temp = node;
                    node = null;
                } 
                else node = temp;

            } else {
                Node temp = maxi(node.left);
                node.key = temp.key;
                node.left = delete(node.left, temp.key);
            }
        }

        if (node == null) return null;
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        return balancing(node);
    }

    Node balancing(Node node) {
        if (node == null) return null;

        int balance = balance(node);

        if ((balance > 1) && (balance(node.left) >= 0)) {
            return singleRightRotate(node);
        }

        if ((balance > 1) && (balance(node.left) < 0)) {
            node.left = singleLeftRotate(node.left);
            return singleRightRotate(node);
        }

        if ((balance < -1) && (balance(node.right) <= 0)) {
            return singleLeftRotate(node);
        }

        if ((balance < -1) && (balance(node.right) > 0)) {
            node.right = singleRightRotate(node.right);
            return singleLeftRotate(node);
        }

        return node;
    }

    Node singleLeftRotate(Node node) {
        Node newNode = node.right;
        node.right = newNode.left;
        newNode.left = node;

        // update tinggi
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newNode.height = Math.max(height(newNode.left), height(newNode.right)) + 1;
        
        return newNode;
    }

    Node singleRightRotate(Node node) {
        Node newNode = node.left;
        node.left = newNode.right;
        newNode.right = node;

        // update tinggi
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newNode.height = Math.max(height(newNode.left), height(newNode.right)) + 1;
        
        return newNode;
    }

    int height(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    int balance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    Node maxi (Node node) {
        Node temp = node;
        while (temp.right != null) temp = temp.right;
        return temp;
    }
}