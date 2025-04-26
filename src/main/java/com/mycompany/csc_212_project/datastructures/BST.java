package com.mycompany.csc_212_project.datastructures;

public class BST<K, V> {

    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private Node current;
    private int size;

    public BST() {
        root = null;
        current = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return size;
    }

    public boolean find(K key) {
        Node node = root;
        current = null;

        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) {
                current = node;
                return true;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    public V getValue() {
        return current != null ? current.value : null;
    }

    public boolean add(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            current = root;
            size++;
            return true;
        }

        Node parent = null;
        Node node = root;

        while (node != null) {
            parent = node;
            int cmp = compare(key, node.key);
            if (cmp == 0) {
                node.value = value; 
                current = node;
                return false;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        Node newNode = new Node(key, value);
        if (compare(key, parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        current = newNode;
        size++;
        return true;
    }

    public boolean remove(K key) {
        boolean[] removed = new boolean[1];
        root = removeNode(root, key, removed);
        if (removed[0]) {
            size--;
            current = null;
        }
        return removed[0];
    }

    private Node removeNode(Node node, K key, boolean[] removed) {
        if (node == null) {
            return null;
        }

        int cmp = compare(key, node.key);

        if (cmp < 0) {
            node.left = removeNode(node.left, key, removed);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, key, removed);
        } else {
            removed[0] = true;

            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node smallest = findSmallest(node.right);
            node.key = smallest.key;
            node.value = smallest.value;
            node.right = removeNode(node.right, smallest.key, new boolean[1]);
        }
        return node;
    }

    private Node findSmallest(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private int compare(K key1, K key2) {
        if (key1 instanceof String && key2 instanceof String) {
            return ((String)key1).compareToIgnoreCase((String)key2);
        }
        if (key1 instanceof Comparable) {
            return ((Comparable<K>)key1).compareTo(key2);
        }
        return Integer.compare(key1.hashCode(), key2.hashCode());
    }
}   