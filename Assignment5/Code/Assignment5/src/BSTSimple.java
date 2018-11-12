/*
 * Author: 2018. Jianwei Zhang
 */

import java.util.*;
import java.util.function.BiFunction;
import java.io.*;

public class BSTSimple<Key extends Comparable<Key>, Value> implements BSTdetail<Key, Value> {
    @Override
    public Boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public void putAll(Map<Key, Value> map) {
        // CONSIDER optionally randomize the input
        for (Map.Entry<Key, Value> entry : map.entrySet()) put(entry.getKey(), entry.getValue());
    }

    @Override
    public int size() {
        return root != null ? root.count : 0;
    }

    @Override
    public void inOrderTraverse(BiFunction<Key, Value, Void> f) {
        doTraverse(0, root, f);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public Value put(Key key, Value value) {    // Modified
        NodeValue nodeValue = put(root, key, value);
        if (root == null) root = nodeValue.node;
        //if (nodeValue.value==null) root.count++;
        return nodeValue.value;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    @Override
    public Set<Key> keySet() {
        return null;
    }

    public BSTSimple() {
    }

    public BSTSimple(Map<Key, Value> map) {
        this();
        putAll(map);
    }

    Node root = null;

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cf = key.compareTo(node.key);
        if (cf < 0) return get(node.smaller, key);
        else if (cf > 0) return get(node.larger, key);
        else return node.value;
    }

    /**
     * Method to put the key/value pair into the subtree whose root is node.
     *
     * @param node  the root of a subtree
     * @param key   the key to insert
     * @param value the value to associate with the key
     * @return a tuple of Node and Value: Node is the
     */
    private NodeValue put(Node node, Key key, Value value) { // Modified
        // If node is null, then we return the newly constructed Node, and value=null
        if (node == null) {
            Node tmp = new Node(key, value);
            tmp.count = 1;
            return new NodeValue(tmp, null);
        }
        int cf = key.compareTo(node.key);
        if (cf == 0) {
            // If keys match, then we return the node and its value
            NodeValue result = new NodeValue(node, node.value);
            node.value = value;
            return result;
        } else if (cf < 0) {
            // if key is less than node's key, we recursively invoke put in the smaller subtree
            NodeValue result = put(node.smaller, key, value);
            if (node.smaller == null)
                node.smaller = result.node;
            if (result.value==null)
                node.count = 1 + size(node.smaller) + size(node.larger);
            return result;
        } else {
            // if key is greater than node's key, we recursively invoke put in the larger subtree
            NodeValue result = put(node.larger, key, value);
            if (node.larger == null)
                node.larger = result.node;
            if (result.value==null)
                node.count = 1 + size(node.smaller) + size(node.larger);
            return result;
        }
    }

    private Node delete(Node x, Key key) {
        // TO IMPLEMENT
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.smaller = delete(x.smaller, key);
        else if (cmp > 0) x.larger = delete(x.larger, key);
        else {
            if (x.larger == null) return x.smaller;
            if (x.smaller == null) return x.larger;

            Node t = x;
            x = min(t.larger);
            x.larger = deleteMin(t.larger);
            x.smaller = t.smaller;
        }
        x.count = size(x.smaller) + size(x.larger) + 1;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.smaller == null) return x.larger;
        x.smaller = deleteMin(x.smaller);
        x.count = 1 + size(x.smaller) + size(x.larger);
        return x;
    }

    private int size(Node x) {
        return x == null ? 0 : x.count;
    }

    private Node min(Node x) {
        if (x == null) throw new RuntimeException("min not implemented for null");
        else if (x.smaller == null) return x;
        else return min(x.smaller);
    }

    /**
     * Do a generic traverse of the binary tree starting with node
     * @param q determines when the function f is invoked ( lt 0: pre, ==0: in, gt 0: post)
     * @param node the node
     * @param f the function to be invoked
     */
    private void doTraverse(int q, Node node, BiFunction<Key, Value, Void> f) {
        if (node == null) return;
        if (q<0) f.apply(node.key, node.value);
        doTraverse(q, node.smaller, f);
        if (q==0) f.apply(node.key, node.value);
        doTraverse(q, node.larger, f);
        if (q>0) f.apply(node.key, node.value);
    }

    private class NodeValue {
        private final Node node;
        private final Value value;

        NodeValue(Node node, Value value) {
            this.node = node;
            this.value = value;
        }

        @Override
        public String toString() {
            return node + "<->" + value;
        }
    }

    class Node {
        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        final Key key;
        Value value;
        Node smaller = null;
        Node larger = null;
        int count = 0;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Node: " + key + ":" + value);
            if (smaller != null) sb.append(", smaller: " + smaller.key);
            if (larger != null) sb.append(", larger: " + larger.key);
            return sb.toString();
        }
    }

    private Node makeNode(Key key, Value value) {
        return new Node(key, value);
    }

    private Node getRoot() {
        return root;
    }

    private void setRoot(Node node) {
        if(root==null){
            root = node;
            root.count++;
        }else
            root = node;
    }

    private void show(Node node, StringBuffer sb, int indent) {
        if (node == null) return;
        for (int i = 0; i < indent; i++) sb.append("  ");
        sb.append(node.key);
        sb.append(": ");
        sb.append(node.value);
        sb.append("\n");
        if (node.smaller != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("smaller: ");
            show(node.smaller, sb, indent + 1);
        }
        if (node.larger != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("larger: ");
            show(node.larger, sb, indent + 1);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        show(root, sb, 0);
        return sb.toString();
    }

    // TODO
    
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.smaller), height(x.larger));
    }

    public static void main(String[] args) {
        class Result {
            double size;
            double height;
            double logSize;
            double sqrtSize;

            Result(double size, double height) {
                this.size = size;
                this.height = height;
                sqrtSize = Math.sqrt(size);
                logSize = Math.log(size);
            }

            @Override
            public String toString() {
                return "height=" + height +
                        ", sqrtSize=" + sqrtSize +
                        ", logSize=" + logSize +
                        '}';
            }
        }

        Map<Integer, Result> resultList = new HashMap<>();

        int start = 10, stop = 1000;
        for (int s = start; s < stop; s++) {                   // s: The initial number of keys in the tree
            ArrayList<Integer> a = new ArrayList<>();
            ArrayList<Integer> initA = new ArrayList<>();
            for (int i = 0; i < s; i++)
                a.add(i);
            generate(a, initA,0,s-1);                 // Initialize an inorder array to initialize the tree as a balanced BST

            int totalHeight = 0, totalSize = 0;
            for (int k = 0; k < 10; k++) {                  // k: Experiment times;
                BSTSimple<Integer, Integer> bst = new BSTSimple<>();
                for (int i : initA) {
                    bst.put(i, 1); // set value always 1
                }
                //bst.resultOutput();
                Random random = new Random();
                for (int i = 0; i < 10000; i++) {          // i: The number of inserts/deletions
                    int key = random.nextInt(2*s-1);
                    if (bst.get(key) != null) {
                        bst.delete(key);
                    } else {
                        bst.put(key, 1);
                    }
                }
                //bst.resultOutput();
                totalHeight += bst.height();
                totalSize += bst.size();
            }
            Result result = new Result((double)totalSize/10, (double)totalHeight/10);
            //that is because we have ten experiments.
            resultList.put(s, result);
        }
        // output
        try {

            FileOutputStream fis = new FileOutputStream("result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            bw.write("Size(N),Experiment Height(h),Log of Size,Sqrt of Size\r\n");
            for (Map.Entry<Integer, Result> result : resultList.entrySet()) {
                System.out.println("Size=" + result.getKey() + ", " + result.getValue());
                bw.write(result.getKey() + "," + result.getValue().height + "," + result.getValue().logSize+ "," + result.getValue().sqrtSize + "\r\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void generate(ArrayList<Integer> a, ArrayList<Integer> initA, int l, int r) {
        if (l>r) return;
        int mid = (l+r)/2;
        initA.add(a.get(mid));
        if (l == r) return;
        generate(a,initA,l,mid-1);
        generate(a,initA,mid+1, r);
    }
}