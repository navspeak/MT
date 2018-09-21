package linkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class GenericLinkedList<T extends Comparable<T>> implements Iterable<T> {
    Node<T> head;
    //constructors
    GenericLinkedList(){};
    GenericLinkedList(T... vals){
        head = new Node(vals[0]);
        Node<T> ptr = head;
        for(int i = 1; i<vals.length;i++){
            ptr.next = new Node(vals[i]);
            ptr = ptr.next;
        }
    }

    public void addToEnd(T val){
        Node<T> ptr = head;
        for(; ptr.next !=null; ptr = ptr.next);
        ptr.next = new Node(val);
    }

    public void addAtBeginning(T val){
        Node<T> ptr = new Node(val);
        ptr.next = head;
        head = ptr;
    }

    public void  sort(){
        head = mergesort(head);
    }
    private Node<T> mergesort(Node<T> node){
        if (node == null || node.next == null) return node;
        Node<T> mid = getMid();
        Node<T> left = node;
        Node<T> right = mid.next;
        mid.next = null;
        mergesort(left);
        mergesort(right);
        return merge(left, right);
    }

    private Node<T> merge(Node<T> left, Node<T> right) {
        Node<T> ptr1 = left;
        Node<T> ptr2 = right;
        Node<T> mergedNode = null;
        // Copy smalles of left and right to mergedList
        if (left.data.compareTo(right.data)<0){
            mergedNode = new Node(ptr1.data);
            ptr1 = ptr1.next;
        } else {
            mergedNode = new Node(ptr2.data);
            ptr2 = ptr2.next;
        }
        Node<T> ptr3 = mergedNode;

        while(ptr1 != null && ptr2 !=null){
            if(ptr1.data.compareTo(ptr2.data)<0) {//ptr/ < ptr2
                ptr3.next = new Node(ptr1.data);
                ptr3 = ptr3.next;
                ptr1 = ptr1.next;
            } else {
                ptr3.next = new Node(ptr2.data);
                ptr3 = ptr3.next;
                ptr2 = ptr2.next;
            }
        }

        if (ptr1 != null){
            while(ptr1 != null){
                ptr3.next = new Node(ptr1.data);
                ptr3 = ptr3.next;
                ptr1 = ptr1.next;
            }
        }
        if (ptr2 != null){
            while(ptr2 != null){
                ptr3.next = new Node(ptr2.data);
                ptr3 = ptr3.next;
                ptr2 = ptr2.next;
            }
        }
        return mergedNode;
    }

    private Node<T> getMid() {
        if (head == null || head.next == null) return head;
        Node<T> slow = head;
        Node<T> fast = head.next;
        while(fast !=null && fast.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Returns a new list that is reversed. it doesn't mutate the original list
     * @return
     */
    public GenericLinkedList<T> reverse() {
        GenericLinkedList<T> reverseList = new GenericLinkedList<>();
        for(T val: this){
            reverseList.addAtBeginning(val);
        }
        return reverseList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(T data: this){
            sb.append(data).append("->");
        }
// Note: Above code uses iterator to accomplish what the below code does
//        Iterator itr = this.iterator();
//        while (itr.hasNext()){
//            sb.append(itr.next()).append("->");
//        }
// OR:
//        for(Node<T> ptr = head; ptr!=null; ptr=ptr.next){
//            sb.append(ptr.data).append("->");
//        }
        sb.append("NULL");
        return sb.toString();
    }

    static class Node<T> {
        T data;
        Node next;

        Node(T val) {
            this.data = val;
        }
        public String toString() {
            return "" + data;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        //https://www.geeksforgeeks.org/fail-fast-fail-safe-iterators-java/
        //We have not implemented fail fast functionality to check for concurrent modification
        private Node<T> nextNode;

        public LinkedListIterator() {
            nextNode = head;
        }

        @Override
        public boolean hasNext() {
            return (nextNode != null);
        }

        @Override
        public T next() {
            if (!hasNext())  throw new NoSuchElementException();
            Node<T> tmp = nextNode;
            nextNode = nextNode.next;
            return tmp.data;
        }
    }
}