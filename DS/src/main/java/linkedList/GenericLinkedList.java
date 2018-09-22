package linkedList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GenericLinkedList<T extends Comparable<T>> implements Iterable<T> {
    Node<T> head;
    Node<T> endNode;
    int length = 0;
    //constructors
    GenericLinkedList(){};
    GenericLinkedList(T... vals){
        head = new Node(vals[0]);
        Node<T> ptr = head;
        for(int i = 1; i<vals.length;i++){
            ptr.next = new Node(vals[i]);
            ptr = ptr.next;
        }
        endNode = ptr;
        length = vals.length;
    }

    public void addToEnd(T val){
        if(length == 0){
            head = new Node(val);
            endNode = head;
        } else {
            assert endNode != null;
            endNode.next = new Node(val);
            endNode = endNode.next;
        }
        length++;
    }

    public void addAtBeginning(T val){
        Node<T> ptr = new Node(val);
        //endNode = ptr;
        ptr.next = head;
        head = ptr;
        length++;
    }

    public GenericLinkedList<T> sort(){
        GenericLinkedList<T> copyOfOrig = new GenericLinkedList<>();
        for(T data: this){
            copyOfOrig.addToEnd(data);
        }
        copyOfOrig.head = mergesort(copyOfOrig.head);
        return copyOfOrig;
    }

    private static<T extends Comparable<T>> Node<T> mergesort(Node<T> head) {
        if(head == null || head.next == null) return head;
        Node<T> mid = getMid(head);
        Node rightHalf = mid.next;
        Node<T> leftHalf = head;
        mid.next = null;

        Node left = mergesort(leftHalf);
        Node right = mergesort(rightHalf);

        return merge(left, right);
    }

    private static<T extends Comparable<T>> Node<T> merge(Node<T> left, Node<T> right) {
        if(left == null)   return right;
        if(right == null)  return left;
        Node temp = null;
        if(left.data.compareTo(right.data)<0) {
            temp = left;
            temp.next = merge(left.next, right);
        } else {
            temp = right;
            temp.next = merge(left, right.next);
        }
        return temp;
    }
    private static<T extends Comparable<T>> Node<T> getMid(Node<T> node) {
        Node<T> slow = node;
        Node<T> fast = node;
        while(fast !=null && fast.next !=null && fast.next.next !=null){
            fast = fast.next.next;
            slow = slow.next;
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
        Node(T val) { this.data = val;}
        public Node() { }
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
        private Node<T> nextNode;
        int expectedModCount;

        public LinkedListIterator() {
            nextNode = head;
            expectedModCount = length;
        }

        @Override
        public boolean hasNext() {
            checkForConcurrentModification();
            return (nextNode != null);
        }

        @Override
        public T next() {
            if (!hasNext())  throw new NoSuchElementException();
            Node<T> tmp = nextNode;
            nextNode = nextNode.next;
            return tmp.data;
        }

        private void checkForConcurrentModification() {
            if (length != expectedModCount) throw new ConcurrentModificationException();
        }
    }
}