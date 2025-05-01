package com.mycompany.csc_212_project.datastructures;



public class BST<K extends Comparable<K>, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root, current;

    public BST() {
        root = current = null;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean full() {
        return false;
    }

    public V retrieve() {
        return current != null ? current.value : null;
    }

    public boolean update(K key, V value) {
        removeKey(key);
        return insert(key, value);
    }

    public boolean findKey(K tkey) {
        BSTNode p = root, q = root;
        if (empty()) return false;

        while (p != null) {
            q = p;
            int cmp = tkey.compareTo(p.key);
            if (cmp == 0) {
                current = p;
                return true;
            } else if (cmp < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        current = q;
        return false;
    }

    public boolean insert(K k, V val) {
        BSTNode p, q = current;
        if (findKey(k)) {
            current = q;
            return false;
        }

        p = new BSTNode(k, val);
        if (empty()) {
            root = current = p;
            return true;
        } else {
            if (k.compareTo(current.key) < 0)
                current.left = p;
            else
                current.right = p;
            current = p;
            return true;
        }
    }

    public boolean removeKey(K key) {
        BooleanWrapper flag = new BooleanWrapper(false);
        root = removeAux(key, root, flag);
        return flag.value;
    }

    private BSTNode removeAux(K key, BSTNode p, BooleanWrapper flag) {
        if (p == null) return null;

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeAux(key, p.left, flag);
        } else if (cmp > 0) {
            p.right = removeAux(key, p.right, flag);
        } else {
            flag.setValue(true);
            if (p.left != null && p.right != null) {
                BSTNode q = findMin(p.right);
                p.key = q.key;
                p.value = q.value;
                p.right = removeAux(q.key, p.right, flag);
            } else {
                return (p.left != null) ? p.left : p.right;
            }
        }
        return p;
    }

    private BSTNode findMin(BSTNode p) {
        while (p.left != null) p = p.left;
        return p;
    }

    private static class BooleanWrapper {
        boolean value;
        BooleanWrapper(boolean val) { this.value = val; }
        void setValue(boolean val) { this.value = val; }
    }
}
