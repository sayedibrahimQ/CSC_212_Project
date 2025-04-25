package com.mycompany.csc_212_project.datastructures;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> current;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

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
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            current = head; 
        } else {
            Node<T> temp = head;
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

        Node<T> previous = head;
        while (previous != null && previous.next != current) {
            previous = previous.next;
        }

        if (previous != null) {
            previous.next = current.next; 
            current = previous.next; 
             size--;
        }
    }


    public int size() {
        return size;
    }

}