package com.mycompany.csc_212_project.datastructures;

public class BST<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;
    private BSTNode current;
    private int size;

    // Initializes an empty BST
    public BST() {
        root = null;
        current = null;
        size = 0;
    }

    // Returns true if the BST is empty
    public boolean empty() {
        return root == null;
    }

    // Returns the number of nodes in the BST
    public int size() {
        return size;
    }

    // Searches for a key and sets current to the found node
    public boolean findkey(K key) {
        BSTNode node = root;
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

    // Retrieves the value of the current node
    public V retrieve() {
        return current != null ? current.value : null;
    }

    // Inserts a new key-value pair, returns false if key exists
    public boolean insert(K key, V value) {
        if (findkey(key)) {
            return false;
        }
        BSTNode newNode = new BSTNode(key, value);
        if (root == null) {
            root = newNode;
            current = newNode;
            size++;
            return true;
        }
        BSTNode parent = null;
        BSTNode node = root;
        while (node != null) {
            parent = node;
            int cmp = compare(key, node.key);
            if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (compare(key, parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        current = newNode;
        size++;
        return true;
    }

    // Removes a node with the given key
    public boolean removeKey(K key) {
        boolean[] removed = new boolean[1];
        root = removeNode(root, key, removed);
        if (removed[0]) {
            size--;
            current = null;
        }
        return removed[0];
    }

    // Recursively removes a node and updates the tree
    private BSTNode removeNode(BSTNode node, K key, boolean[] removed) {
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
            BSTNode smallest = findSmallest(node.right);
            node.key = smallest.key;
            node.value = smallest.value;
            node.right = removeNode(node.right, smallest.key, new boolean[1]);
        }
        return node;
    }

    // Finds the node with the smallest key in a subtree
    private BSTNode findSmallest(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Compares two keys, supports strings and comparables
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