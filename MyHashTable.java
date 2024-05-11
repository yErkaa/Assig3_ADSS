import java.util.Random;

public class MyHashTable<K, V> {
    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        this.chainArray = new HashNode[this.M];
        this.size = 0;
    }

    public MyHashTable(int M) {
        this.M = M;
        this.chainArray = new HashNode[M];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % this.M;
    }

    public void put(K key, V value) {
        int index = this.hash(key);
        HashNode<K, V> newNode = new HashNode(key, value);
        if (this.chainArray[index] == null) {
            this.chainArray[index] = newNode;
        } else {
            HashNode current;
            for(current = this.chainArray[index]; current.next != null; current = current.next) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
            }

            current.next = newNode;
        }

        ++this.size;
    }

    public V get(K key) {
        int index = this.hash(key);

        for(HashNode<K, V> current = this.chainArray[index]; current != null; current = current.next) {
            if (current.key.equals(key)) {
                return current.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = this.hash(key);
        HashNode<K, V> current = this.chainArray[index];

        for(HashNode<K, V> prev = null; current != null; current = current.next) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    this.chainArray[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                --this.size;
                return current.value;
            }

            prev = current;
        }

        return null;
    }

    public boolean contains(V value) {
        HashNode[] var2 = this.chainArray;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            HashNode<K, V> node = var2[var4];

            for(HashNode<K, V> current = node; current != null; current = current.next) {
                if (current.value.equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }

    public K getKey(V value) {
        HashNode[] var2 = this.chainArray;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            HashNode<K, V> node = var2[var4];

            for(HashNode<K, V> current = node; current != null; current = current.next) {
                if (current.value.equals(value)) {
                    return current.key;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable();
        new Random();

        int i;
        for(i = 0; i < 10000; ++i) {
            MyTestingClass key = new MyTestingClass("Element" + i);
            Student value = new Student("Mansur", 17, "0001");
            table.put(key, value);
        }

        for(i = 0; i < table.M; ++i) {
            int count = 0;

            for(HashNode<MyTestingClass, Student> current = table.chainArray[i]; current != null; current = current.next) {
                ++count;
            }

            System.out.println("Bucket " + i + ": " + count + " elements");
        }

    }

    public static class Student {
        private String name;
        private int age;
        private String studentId;

        public Student(String name, int age, String studentId) {
            this.name = name;
            this.age = age;
            this.studentId = studentId;
        }
    }

    public static class MyTestingClass {
        private String data;

        public MyTestingClass(String data) {
            this.data = data;
        }

        public int hashCode() {
            int hash = 0;

            for(int i = 0; i < this.data.length(); ++i) {
                hash = 31 * hash + this.data.charAt(i);
            }

            return hash;
        }
    }

    private static class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }
}