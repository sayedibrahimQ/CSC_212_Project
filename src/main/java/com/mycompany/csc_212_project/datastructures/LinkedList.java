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

    // Initializes an empty linked list
    public LinkedList() {
        head = null;
        current = null;
        size = 0;
    }

    // Returns true if the list is empty
    public boolean empty() {
        return head == null;
    }

    // Returns true if current is at the last node or null
    public boolean last() {
        return current == null || current.next == null;
    }

    // Sets current to the first node
    public void findFirst() {
        current = head;
    }

    // Moves current to the next node
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    // Retrieves the data of the current node
    public T retrieve() {
        return current != null ? current.data : null;
    }

    // Updates the data of the current node
    public void update(T data) {
        if (current != null) {
            current.data = data;
        }
    }

    // Inserts a new node after current or at the end
    public void insert(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            current = head;
            size++;
            return;
        }
        if (current == null) {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            size++;
            return;
        }
        newNode.next = current.next;
        current.next = newNode;
        current = newNode;
        size++;
    }

    // Removes the current node and updates current
    public void remove() {
        if (current == null || head == null) {
            return;
        }
        if (current == head) {
            head = head.next;
            current = head;
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

    // Returns the number of nodes in the list
    public int size() {
        return size;
    }
}