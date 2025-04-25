package com.mycompany.csc_212_project.datastructures;

public class LinkedList<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node current;
    private int size;

    public LinkedList() {
        head = null;
        current = null;
        size = 0;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current == null || current.next == null;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    public T retrieve() {
        if (current != null) {
            return current.data;
        }
        return null;
    }

    public void update(T data) {
        if (current != null) {
            current.data = data;
        }
    }

    public void insert(T data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            current = head;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    public void remove() {
        if (current == null || head == null) {
            return;
        }

        if (current == head) {
            head = head.next;
            if (head == null) {
                current = null;
            } else {
                current = head;
            }
            size--;
            return;
        }

        Node prev = head;
        while (prev != null && prev.next != current) {
            prev = prev.next;
        }

        if (prev != null) {
            prev.next = current.next;
            current = prev.next;
            size--;
        }
    }

    public int size() {
        return size;
    }
}