import java.io.*;
import java.util.*;

public class Main {
    private static boolean[] isUsed;
    private static int vertexesNum;
    private static Queue<Integer> queue;
    private static int[] path;

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner("input.in");
        PrintWriter pw = new PrintWriter("output.out");
        vertexesNum = fs.nextInt();
        int arcsNum = fs.nextInt();
        isUsed = new boolean[2 * vertexesNum + 1];
        int[][] adjacencyMtr = new int[vertexesNum + 1][vertexesNum + 1];
        int[][] mtr = new int[2 * vertexesNum + 1][2 * vertexesNum + 1];
        int tmp;
        for (int i = 1; i <= vertexesNum; i++) {
            tmp = fs.nextInt();
            while (tmp != 0) {
                adjacencyMtr[i][tmp] = 1;
                tmp = fs.nextInt();
            }
        }
        int begin = fs.nextInt();
        int end = fs.nextInt();
        for (int i = 1; i <= vertexesNum; i++) {
            for (int j = 1; j <= vertexesNum; j++) {
                System.out.print(adjacencyMtr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(begin);
        System.out.println(end);
        for (int i = 1; i <= vertexesNum; i++) {
            for (int j = 1; j <= vertexesNum; j++) {
                if (adjacencyMtr[i][j] == 1) {
                    if (j == begin || j == end) {
                        mtr[vertexesNum + i][vertexesNum + j] = 1;
                    } else {
                        mtr[vertexesNum + i][j] = 1;
                    }
                }
            }
            if (i != begin && i != end) {
                mtr[i][vertexesNum + i] = 1;
                mtr[vertexesNum + i][i] = 1;
            }
        }
        System.out.println();
        for (int i = 1; i <= 2 * vertexesNum; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        for (int i = 1; i <= 2 * vertexesNum; i++) {
            for (int j = 1; j <= 2 * vertexesNum; j++) {
                System.out.printf("%2d ", mtr[i][j]);
            }
            System.out.println(i);
        }
        begin += vertexesNum;
        end += vertexesNum;
        queue = new ArrayDeque<>();
        queue.add(begin);
        path = new int[2 * vertexesNum + 1];
        for (int i = 1; i <= 2 * vertexesNum; i++) {
            path[i] = -1;
        }
        path[begin] = 0;
        int min;
        int current;
        int prev;
        int flow = 0;
        while(bfs(mtr, begin, end)) {
            isUsed[end] = false;
            min = Integer.MAX_VALUE;
            current = end;
            prev = path[current];
            while (prev != 0) {
                System.out.print(current + " ");
                current = prev;
                prev = path[current];
            }
            System.out.print(current);
            System.out.println();
            current = end;
            prev = path[current];
            while (prev != 0) {
                System.out.println(mtr[prev][current]);
                if (mtr[prev][current] < min) {
                    min = mtr[prev][current];
                }
                current = prev;
                prev = path[current];
            }
            System.out.println("min " + min);
            current = end;
            prev = path[current];
            while (prev != 0) {
                mtr[prev][current] -= min;
                mtr[current][prev] += min;
                current = prev;
                prev = path[current];
            }
            flow += min;
        }
        System.out.println(flow);
//        System.out.println(bfs(mtr, 9, 10));
//        System.out.println(queue.peek());
//
//        isUsed[end] = false;
//        System.out.println(bfs(mtr, 9, 10));
//        System.out.println(queue.peek());
//
//        isUsed[end] = false;
//        System.out.println(bfs(mtr, 9, 10));
//        System.out.println(queue.peek());

//        Vertex[] inputStreams = new Vertex[vertexesNum + 1];
//        Vertex[] outputStreams = new Vertex[vertexesNum + 1];
//        for (int i = 1; i <= vertexesNum; i++) {
//            inputStreams[i] = new Vertex();
//            outputStreams[i] = new Vertex();
//        }
//        for (int i = 1; i <= vertexesNum; i++) {
//            for (int j = 1; j <= vertexesNum; j++) {
//                if (adjacencyMtr[i][j] == 1) {
//                    if (j == begin || j == end) {
//                        outputStreams[i].adjArcs.add(new Arc(1, outputStreams[j]));
//                    } else {
//                        outputStreams[i].adjArcs.add(new Arc(1, inputStreams[j]));
//                    }
//                }
//            }
//            outputStreams[i].value = i;
//            if (i != begin && i != end) {
//                inputStreams[i].value = i;
//                inputStreams[i].adjArcs.add(new Arc(1, outputStreams[i]));
//            }
//        }
//        for (int i = 1; i <= vertexesNum; i++) {
//            System.out.println(inputStreams[i].value + ": ");
//            for (Arc cur : inputStreams[i].adjArcs) {
//                System.out.println(cur.weight + " " + cur.next.value);
//            }
//        }
//        System.out.println();
//        for (int i = 1; i <= vertexesNum; i++) {
//            System.out.println(outputStreams[i].value + ": ");
//            for (Arc cur : outputStreams[i].adjArcs) {
//                System.out.println(cur.weight + " " + cur.next.value);
//            }
//        }
    }

    private static boolean bfs(int[][] graph, int from, int to) {
        Integer curPoint;


        while ((curPoint = queue.poll()) != null) {
            if (curPoint == to) {
                isUsed[curPoint] = true;
                break;
            }
            if (!isUsed[curPoint]) {
                for (int i = 1; i <= 2 * vertexesNum; i++) {
                    if (!isUsed[i] && graph[curPoint][i] != 0) {
                        queue.add(i);
                        path[i] = curPoint;
                    }
                }
            }
            isUsed[curPoint] = true;
        }
        System.out.println();
        for (int i = 1; i <= 2 * vertexesNum; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.println();
        for (int i = 1; i <= 2 * vertexesNum; i++) {
            System.out.print(isUsed[i] + " ");
        }
        System.out.println();
        return isUsed[to];
    }


}

class FastScanner {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public FastScanner(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(fileName));
    }

    public String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = reader.readLine();
            if (line == null) {
                throw new EOFException();
            }
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public void close() throws IOException {
        reader.close();
    }
}

class Vertex {
    int value;
    List<Arc> adjArcs;

    Vertex() {
        adjArcs = new ArrayList<>();
    }
}

class Arc {
    int weight;
    Vertex next;

    Arc(int weight, Vertex next) {
        this.weight = weight;
        this.next = next;
    }
}