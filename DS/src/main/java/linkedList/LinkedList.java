package linkedList;

public class LinkedList {
     static class Node {
         int data;
         Node next;
         Node(int val) { this.data = val;}
         public String toString() { return ""+data;}
     }

     public static void print(Node node){
         for (Node ptr = node; ptr!=null; ptr = ptr.next){
             System.out.print(ptr+"->");
         }
         System.out.print("NULL\n");
     }

     public static Node createList(int... vals){
         Node head = new Node(vals[0]);
         Node curr = head;
         for(int i = 1; i< vals.length;i++){
             curr.next = new Node(vals[i]);
             curr = curr.next;
         }
         return head;
     }

     public static Node reverse_rec(Node head){
         //(first)1->2->3->4->5
         // Reverse(first.next)1->2<-3<-4<-5
         //first.next.next = first 1<-2<-3<-4<-5
         if (head == null || head.next == null) return head;
         Node rev = reverse_rec(head.next);
         head.next.next = head;
         head.next = null;
         return rev;
     }

     public static Node reverse_itr(Node head){
         //  p/n  (c)1>2>3>4
         //   1<2(p) (c)3>4
         //   1<2<(p)3 4(c)
         //   1<2<3<4(p) (c)
         Node curr, prev = null, next=null;
         curr = head;
         while(curr!=null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
         }
         return prev;
     }
    public static void main(String[] args) {
        Node head = createList(1,2,3,4,5);
        print(head);

        Node rev = reverse_rec(head);
        print(rev);
        print(reverse_itr(rev));
    }
}
