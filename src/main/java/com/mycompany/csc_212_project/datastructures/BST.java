package com.mycompany.csc_212_project.datastructures;

public class BST<K extends Comparable<K>, T> {

    private BSTNode<K, T> root;
    private BSTNode<K, T> current; 
    private int size;

    private static class BSTNode<K extends Comparable<K>, T> {
        K key;
        T data;
        BSTNode<K, T> left, right;

        BSTNode(K key, T data) {
            this.key = key;
            this.data = data;
            left = right = null;
        }
    }

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
        BSTNode<K, T> p = root;
        current = null; 

        while (p != null) {
            int comparison = key.compareTo(p.key);
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

    public T retrieve() {
        if (current != null) {
            return current.data;
        }
        return null; 
    }

    public boolean insert(K key, T data) {
        if (root == null) {
            root = new BSTNode<>(key, data);
            current = root; 
            size++;
            return true;
        }

        BSTNode<K, T> p = root;
        BSTNode<K, T> q = null; 

        while (p != null) {
            q = p;
            int comparison = key.compareTo(p.key);
            if (comparison == 0) {
                 p.data = data; 
                 current = p; 
                 return false; 
            } else if (comparison < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        BSTNode<K, T> newNode = new BSTNode<>(key, data);
        if (key.compareTo(q.key) < 0) {
            q.left = newNode;
        } else {
            q.right = newNode;
        }
        current = newNode; 
        size++;
        return true; 
    }

    public boolean removeKey(K key) {
        if (root == null) {
            return false; 
        }

        Wrapper removed = new Wrapper(false); 
        root = removeRecursive(root, key, removed);

        if(removed.value){
            size--; 
            current = null; 
        }
        return removed.value;
    }

    private static class Wrapper{
        boolean value;
        Wrapper(boolean v){ value = v;}
    }


    private BSTNode<K, T> removeRecursive(BSTNode<K, T> node, K key, Wrapper removed) {
        if (node == null) {
            return null; 
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            node.left = removeRecursive(node.left, key, removed);
        } else if (comparison > 0) {
            node.right = removeRecursive(node.right, key, removed);
        } else {
            removed.value = true; 

            if (node.left == null && node.right == null) {
                return null;
            else if (node.left == null) {
                return node.right; 
            } else if (node.right == null) {
                return node.left; 
            }
            else {
                BSTNode<K, T> successor = findMin(node.right);
                node.key = successor.key;
                node.data = successor.data;
                node.right = removeRecursive(node.right, successor.key, new Wrapper(false)); 
            }
        }
        return node; 
    }

    private BSTNode<K, T> findMin(BSTNode<K, T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}