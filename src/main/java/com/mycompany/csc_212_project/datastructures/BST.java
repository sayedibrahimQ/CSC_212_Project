package com.mycompany.csc_212_project.datastructures;

public class BST<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }

    private BSTNode root;
    private BSTNode current;
    private int size;

    public BST() {
        root = null;
        current = null;
        size = 0;
    }

    public boolean empty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean findKey(K key) {
        BSTNode p = root;
        current = null;

        while (p != null) {
            int comparison = compare(key, p.key);
            if (comparison == 0) {
                current = p;
                return true;
            } else if (comparison < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return false;
    }

    public V retrieve() {
        if (current != null) {
            return current.value;
        }
        return null;
    }

    public boolean insert(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value);
            current = root;
            size++;
            return true;
        }

        BSTNode p = root;
        BSTNode q = null;

        while (p != null) {
            q = p;
            int comparison = compare(key, p.key);
            if (comparison == 0) {
                p.value = value; // Update existing value
                current = p;
                return false; // No new node added
            } else if (comparison < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        BSTNode newNode = new BSTNode(key, value);
        if (compare(key, q.key) < 0) {
            q.left = newNode;
        } else {
            q.right = newNode;
        }
        current = newNode;
        size++;
        return true;
    }

    public boolean removeKey(K key) {
        // Use a recursive helper method
        boolean[] removed = new boolean[1];
        root = removeRecursive(root, key, removed);
        
        if (removed[0]) {
            size--;
            current = null; // Reset current after deletion
        }
        
        return removed[0];
    }

    private BSTNode removeRecursive(BSTNode node, K key, boolean[] removed) {
        if (node == null) {
            return null;
        }

        int comparison = compare(key, node.key);

        if (comparison < 0) {
            node.left = removeRecursive(node.left, key, removed);
        } else if (comparison > 0) {
            node.right = removeRecursive(node.right, key, removed);
        } else {
            // Found the node to delete
            removed[0] = true;

            // Case 1: Node has no children (leaf)
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: Node has only one child
            else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Case 3: Node has two children
            else {
                // Find smallest value in right subtree
                BSTNode successor = findMin(node.right);
                // Copy successor's data to this node
                node.key = successor.key;
                node.value = successor.value;
                // Delete the successor
                node.right = removeRecursive(node.right, successor.key, new boolean[1]);
            }
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Helper method to compare keys
    @SuppressWarnings("unchecked")
    private int compare(K key1, K key2) {
        // Handle String comparison specially
        if (key1 instanceof String && key2 instanceof String) {
            return ((String)key1).compareToIgnoreCase((String)key2);
        }
        
        // For other types that might implement Comparable
        if (key1 instanceof Comparable) {
            return ((Comparable<K>)key1).compareTo(key2);
        }
        
        // Fallback for non-Comparable types - use hashCode
        return Integer.compare(key1.hashCode(), key2.hashCode());
    }
}