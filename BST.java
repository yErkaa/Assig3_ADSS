import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<Entry<K, V>> {
    private Node root;
    private int size;

    public BST() {
    }

    public void put(K key, V val) {
        this.root = this.put(this.root, key, val);
        ++this.size;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator(this.root);
    }

    private Node<K, V> put(Node<K, V> x, K key, V val) {
        if (x == null) {
            return new Node(key, val);
        } else {
            int cmp = key.compareTo((Comparable)x.key);
            if (cmp < 0) {
                x.left = this.put(x.left, key, val);
            } else if (cmp > 0) {
                x.right = this.put(x.right, key, val);
            } else {
                x.val = val;
            }

            return x;
        }
    }

    public V get(K key) {
        Node<K, V> x = this.root;

        while(x != null) {
            int cmp = key.compareTo((Comparable)x.key);
            if (cmp < 0) {
                x = x.left;
            } else {
                if (cmp <= 0) {
                    return x.val;
                }

                x = x.right;
            }
        }

        return null;
    }

    public void delete(K key) {
        this.root = this.delete(this.root, key);
        --this.size;
    }

    private Node<K, V> delete(Node<K, V> x, K key) {
        if (x == null) {
            return null;
        } else {
            int cmp = key.compareTo((Comparable)x.key);
            if (cmp < 0) {
                x.left = this.delete(x.left, key);
            } else if (cmp > 0) {
                x.right = this.delete(x.right, key);
            } else {
                if (x.right == null) {
                    return x.left;
                }

                if (x.left == null) {
                    return x.right;
                }

                Node<K, V> t = x;
                x = this.min(x.right);
                x.right = this.deleteMin(t.right);
                x.left = t.left;
            }

            return x;
        }
    }

    private Node<K, V> min(Node<K, V> x) {
        return x.left == null ? x : this.min(x.left);
    }

    private Node<K, V> deleteMin(Node<K, V> x) {
        if (x.left == null) {
            return x.right;
        } else {
            x.left = this.deleteMin(x.left);
            return x;
        }
    }

    public Iterable<K> keys() {
        List<K> keys = new ArrayList();
        Stack<Node<K, V>> stack = new Stack();

        for(Node<K, V> current = this.root; current != null || !stack.isEmpty(); current = current.right) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }

            current = (Node)stack.pop();
            keys.add((Comparable)current.key);
        }

        return keys;
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST();
        bst.put(5, "Five");
        bst.put(3, "Three");
        bst.put(7, "Seven");
        bst.put(2, "Two");
        bst.put(4, "Four");
        bst.put(6, "Six");
        bst.put(8, "Eight");
        System.out.println("Size: " + bst.size());
        System.out.println("In-order traversal:");
        Iterator var2 = bst.iterator();

        while(var2.hasNext()) {
            Entry<Integer, String> entry = (Entry)var2.next();
            PrintStream var10000 = System.out;
            Object var10001 = entry.getKey();
            var10000.println("Key is " + var10001 + " and value is " + (String)entry.getValue());
        }

    }

    public static class Entry<K, V> {
        private K key;
        private V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.val;
        }
    }

    private class BSTIterator implements Iterator<Entry<K, V>> {
        private Stack<Node<K, V>> stack = new Stack();

        public BSTIterator(Node<K, V> root) {
            this.pushLeft(root);
        }

        private void pushLeft(Node<K, V> node) {
            while(node != null) {
                this.stack.push(node);
                node = node.left;
            }

        }

        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        public Entry<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                Node<K, V> node = (Node)this.stack.pop();
                this.pushLeft(node.right);
                return new Entry((Comparable)node.key, node.val);
            }
        }
    }

    private static class Node<K, V> {
        private K key;
        private V val;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
