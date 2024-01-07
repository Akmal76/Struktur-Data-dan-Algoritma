// import java.util.*;
// import java.io.*;

// public class Lab7 {

//     static class Box {
//         int id;
//         long value;
//         String state;

//         Box(int id, long value, String state) {
//             this.id = id;
//             this.value = value;
//             this.state = state;
//         }

//         public int compareTo(Box box) {
//             if (this.value == box.value) return this.id - box.id;
//             return (int) (this.value - box.value);
//         }
//     }

//     static class BoxContainer {
//         public ArrayList<Box> heap;
//         public int size;
//         public HashMap<Integer, Integer> idToIndexMap;

//         public BoxContainer() {
//             this.heap = new ArrayList<>();
//             this.idToIndexMap = new HashMap<>();
//         }

//         public void percolateUp(int i) {
//             // TO DO
//             while (hasParent(i) && getParent(i).compareTo(heap.get(i)) < 0) {
//                 swap(getParentIndex(i), i);
//                 i = getParentIndex(i);
//             }
//         }

//         public void percolateDown(int i) {
//             // TO DO
//             heap.set(0, heap.get(--size - 1));
//             while (hasLeftChild(i)) {
//                 int maxChildIndex = getLeftChildIndex(i);
//                 if (hasRightChild(i) && getRightChild(i).compareTo(getLeftChild(i)) > 0) maxChildIndex = getRightChildIndex(i);
//                 if (heap.get(i).compareTo(heap.get(maxChildIndex)) > 0) break;
//                 else swap(i, maxChildIndex);
//                 i = maxChildIndex;
//             }
//         }

//         public void insert(Box box) {
//             // TO DO
//             heap.add(box);
//             size++;
//             percolateUp(size - 1);
//         }

//         public Box peek() {
//             return heap.get(0);
//         }

//         public void swap(int firstIndex, int secondIndex) {
//             // TO DO
//             Box temp = heap.get(firstIndex);
//             heap.set(firstIndex, heap.get(secondIndex));
//             heap.set(secondIndex, temp);
//         }

//         public void updateBox(Box box) {
//             // TO DO
            
//         }

//         public boolean isEmpty() { return size == 0; }
//         public static int getParentIndex(int i) { return (i - 1) / 2; }
//         public static int getLeftChildIndex(int i) { return 2 * i + 1; }
//         public static int getRightChildIndex(int i) { return 2 * i + 2; }
//         public boolean hasParent(int i) { return getParentIndex(i) >= 0; }
//         public boolean hasLeftChild(int i) { return getLeftChildIndex(i) < size; }
//         public boolean hasRightChild(int i) { return getRightChildIndex(i) < size; }
//         public Box getParent(int i) { return heap.get(getParentIndex(i)); }
//         public Box getLeftChild(int i) { return heap.get(getLeftChildIndex(i)); }
//         public Box getRightChild(int i) { return heap.get(getRightChildIndex(i)); }
//     }

//     public static void main(String[] args) throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         PrintWriter pw = new PrintWriter(System.out);
    
//         int N = Integer.parseInt(br.readLine());
    
//         ArrayList<Box> boxes = new ArrayList<>();
//         BoxContainer boxContainer = new BoxContainer();
    
//         for (int i = 0; i < N; i++) {
//             StringTokenizer st = new StringTokenizer(br.readLine());
//             long value = Long.parseLong(st.nextToken());
//             String state = st.nextToken();
    
//             Box box = new Box(boxes.size(), value, state);
//             boxes.add(box);
//             boxContainer.insert(box);
//         }
    
//         int T = Integer.parseInt(br.readLine());
    
//         for (int i = 0; i < T; i++) {
//             StringTokenizer st = new StringTokenizer(br.readLine());
//             String command = st.nextToken();
    
//             if ("A".equals(command)) {
//                 // TO DO
//                 long V = Long.parseLong(st.nextToken());
//                 String S = st.nextToken();

//                 Box box = new Box(boxes.size(), V, S);
//                 boxes.add(box);
//                 boxContainer.insert(box);

//             } else if ("D".equals(command)) {
//                 // TO DO
//                 int I = Integer.parseInt(st.nextToken());
//                 int J = Integer.parseInt(st.nextToken());

//                 Box boxI = boxes.get(I);
//                 Box boxJ = boxes.get(J);

//                 if (boxI.state.equals("R") && boxJ.state.equals("S") ||
//                     boxI.state.equals("P") && boxJ.state.equals("R") ||
//                     boxI.state.equals("S") && boxJ.state.equals("P")) {
//                     // Update box
//                     boxI.value += boxJ.value;
//                     boxJ.value /= 2;
//                 } else if (
//                     boxJ.state.equals("R") && boxI.state.equals("S") ||
//                     boxJ.state.equals("P") && boxI.state.equals("R") ||
//                     boxJ.state.equals("S") && boxI.state.equals("P")) {
//                     // Update box
//                     boxJ.value += boxI.value;
//                     boxI.value /= 2;
//                 }
                
//             } else if ("N".equals(command)) {
//                 // TO DO
//             }

//             Box topBox = boxContainer.peek();
//             pw.println(topBox.value + " " + topBox.state);
//         }
    
//         pw.flush();
//         pw.close();
//     }    
// }

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Lab7 {

    static class Box implements Comparable<Box> {
        int id;
        long value;
        String state;

        Box(int id, long value, String state) {
            this.id = id;
            this.value = value;
            this.state = state;
        }

        @Override
        public int compareTo(Box box) {
            if (this.value == box.value) return this.id - box.id;
            return (int) (this.value - box.value);
        }

        @Override
        public String toString() {
            return "\nBox{" + "id= " + id +", value= " + value + ", state='" + state + '\'' + "}";
        }
    }

    static class BoxContainer {
        public ArrayList<Box> heap;
        public int size;
        public HashMap<Integer, Integer> idToIndexMap;

        public BoxContainer() {
            this.heap = new ArrayList<>();
            this.idToIndexMap = new HashMap<>();
        }

        public static int getParentIndex(int i)     { return (i - 1) / 2; }
        public static int getLeftChildIndex(int i)  { return 2 * i + 1; }
        public static int getRightChildIndex(int i) { return 2 * i + 2; }

        public boolean hasParent(int i)     { return getParentIndex(i) >= 0; }
        public boolean hasLeftChild(int i)  { return getLeftChildIndex(i) < size; }
        public boolean hasRightChild(int i) { return getRightChildIndex(i) < size; }

        public Box getParent(int i)     { return heap.get(getParentIndex(i)); }
        public Box getLeftChild(int i)  { return heap.get(getLeftChildIndex(i)); }
        public Box getRightChild(int i) { return heap.get(getRightChildIndex(i)); }

        public void percolateUp(int i) {
            // Percolate up as long as the parent is greater than the current element
            while (hasParent(i) && getParent(i).compareTo(heap.get(i)) < 0) {
                swap(getParentIndex(i), i);
                i = getParentIndex(i);
            }
        }

        public void percolateDown(int i) {
            // Precolate down as long as the current element is greater than one of its children
            while (hasLeftChild(i)) {
                int smallerChildIndex = getLeftChildIndex(i);
                if (hasRightChild(i) && getRightChild(i).compareTo(getLeftChild(i)) > 0) smallerChildIndex = getRightChildIndex(i);
                if (heap.get(i).compareTo(heap.get(smallerChildIndex)) > 0) break;
                else swap(i, smallerChildIndex);

                i = smallerChildIndex;
            }
        }

        public void insert(Box box) {
            // Insert heap
            heap.add(box);
            idToIndexMap.put(box.id, size);
            percolateUp(++size - 1);
        }

        public Box peek() {
            Box box = heap.get(0);
            heap.set(0, heap.get(--size));
            percolateDown(0);
            return box;
        }

        public void swap(int firstIndex, int secondIndex) {
            // Swap heap
            Box temp = heap.get(firstIndex);
            heap.set(firstIndex, heap.get(secondIndex));
            heap.set(secondIndex, temp);
            // Update id to index map
            idToIndexMap.put(heap.get(firstIndex).id, firstIndex);
            idToIndexMap.put(heap.get(secondIndex).id, secondIndex);
        }

        public void updateBox(Box box) {
            // Update box
            int index = idToIndexMap.get(box.id);
            heap.set(index, box);
            percolateUp(index);
            percolateDown(index);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
    
        int N = Integer.parseInt(br.readLine());
    
        ArrayList<Box> boxes = new ArrayList<>();
        BoxContainer boxContainer = new BoxContainer();
    
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long value = Long.parseLong(st.nextToken());
            String state = st.nextToken();
    
            Box box = new Box(boxes.size(), value, state);
            boxes.add(box);
            boxContainer.insert(box);
        }
        System.err.println("BEFORE QUERY:");
        System.err.println("--------------------");
        System.err.println("HEAP: " + boxContainer.heap + "\n");
        System.err.println("ID TO INDEX MAP: " + boxContainer.idToIndexMap + "\n");
        System.err.println("BOXES: " + boxes + "\n");
    
        int T = Integer.parseInt(br.readLine());
    
        for (int i = 0; i < T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
    
            if ("A".equals(command)) {
                int value = Integer.parseInt(st.nextToken());
                String state = st.nextToken();
                // Add new box
                Box box = new Box(boxes.size(), value, state);
                boxes.add(box);
                boxContainer.insert(box);
            } else if ("D".equals(command)) {
                int idI = Integer.parseInt(st.nextToken());
                int idJ = Integer.parseInt(st.nextToken());
                // Get box by id
                Box boxI = boxes.get(idI);
                Box boxJ = boxes.get(idJ);
                if (boxI.state.equals("R") && boxJ.state.equals("S") ||
                    boxI.state.equals("P") && boxJ.state.equals("R") ||
                    boxI.state.equals("S") && boxJ.state.equals("P")) {
                    // Update box
                    boxI.value += boxJ.value;
                    boxJ.value /= 2;
                } else if (
                    boxJ.state.equals("R") && boxI.state.equals("S") ||
                    boxJ.state.equals("P") && boxI.state.equals("R") ||
                    boxJ.state.equals("S") && boxI.state.equals("P")) {
                    // Update box
                    boxJ.value += boxI.value;
                    boxI.value /= 2;
                }
                boxContainer.updateBox(boxI);
                boxContainer.updateBox(boxJ);
            } else if ("N".equals(command)) {
                int id = Integer.parseInt(st.nextToken());
                Box kiri = null, tengah = boxes.get(id), kanan = null;

                if (id != boxes.size() - 1) kanan = boxes.get(id + 1);
                if (id != 0)                kiri = boxes.get(id - 1);
                
                if (kiri != null && tengah.state.equals("R") && kiri.state.equals("S") ||
                    kiri != null && tengah.state.equals("P") && kiri.state.equals("R") ||
                    kiri != null && tengah.state.equals("S") && kiri.state.equals("P")) {
                    // Update box
                    tengah.value += kiri.value;
                } else if (
                    kanan != null && tengah.state.equals("R") && kanan.state.equals("S") ||
                    kanan != null && tengah.state.equals("P") && kanan.state.equals("R") ||
                    kanan != null && tengah.state.equals("S") && kanan.state.equals("P")) {
                    // Update box
                    tengah.value += kanan.value;
                }
                boxContainer.updateBox(tengah);
            }

            System.err.println("AFTER QUERY " + i + " " + command + " :");
            System.err.println("--------------------");
            System.err.println("HEAP: " + boxContainer.heap + "\n");
            System.err.println("ID TO INDEX MAP: " + boxContainer.idToIndexMap + "\n");
            System.err.println("BOXES: " + boxes + "\n");

            Box topBox = boxContainer.peek();
            pw.println(topBox.value + " " + topBox.state);
        }
    
        pw.flush();
        pw.close();
    }    
}