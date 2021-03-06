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

    public static Node reverse_ingroup(Node head, int k){
        // 1>2>3>4>5>6>7>8> and 3
        // 3>2>1>6>5>4>7>8
        int len = getLen(head);
        Node curr = head;
        Node prev = null;
        Node next = null;
        int i = 0;
        while (curr!=null && i < k){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            i++;
            //(C)1>2(N)>3>4>5>6>7>8 => <(P)1 2(C/N)>3>4>5>6>7>8 i=1
            // <(P)1 2(C)>3(N)>4>5>6>7>8 =>  <1<2(P) 3(C/N)>4>5>6>7>8 i=2

        }
        int remLen = len -i;
        if (next != null && remLen > k){
            head.next = reverse_ingroup(next, 3);
        } else if (remLen < k){
//            1->2->3->4->5->6->7->8->NULL
//            3->2->1->6->5->4->7->8->NULL (we don't want to reverse 7->8 as it is less than 3
            head.next = next;
        }
        return prev;
    }

    private static int getLen(Node head) {
        int len = 0;
        for(Node ptr = head; ptr !=null; ptr=ptr.next, len++) ;
        return len;
    }

    public static void main(String[] args) {
        Node head = createList(1,2,3,4,5);
        print(head);

        Node rev = reverse_rec(head);
        print(rev);
        print(reverse_itr(rev));

        Node head2 = createList(1,2,3,4,5,6,7,8);
        print(head2);
        print(reverse_ingroup(head2, 3));

        Node list1 = createList(2,6,8,9);
        Node list2 = createList(1,2,4,6,9);
        print(list1);
        print(list2);
        Node mergedList = mergeSorted(list1, list2); //mutates list1, list2
        print(mergedList);// 1->2->2->4->6->6->8->9->9->NULL

        System.out.println("Merge K Lists");
        Node listA = createList(2,6,8,9);
        Node listB = createList(1,2,4,6,9);
        Node listC = createList(0,6,7,8);
        Node listD = createList(-2, 5,12,90);
        print(listA);
        print(listB);
        print(listC);
        print(listD);
        Node[] nodes = {listA, listB, listC, listD};
        Node mergedKList = mergeKSortedList(nodes, 0, nodes.length - 1); //mutates list1, list2
        print(mergedKList);// 1->2->2->4->6->6->8->9->9->NULL
    }

    private static Node mergeSorted(Node list1, Node list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        Node tmp = null;
        if (list1.data < list2.data){
            tmp = list1;
            tmp.next = mergeSorted(list1.next, list2);
        } else{
            tmp = list2;
            tmp.next = mergeSorted(list1, list2.next);
        }
        return tmp;
    }


    private static Node mergeKSortedList(Node[] nodes, int start, int end){
        int length = end - start + 1;
        if (length == 0 ) return null;
        if (length == 1 ) return nodes[start];
        if (length == 2 ) {
            return mergeSorted(nodes[start], nodes[end]);
        }
        int mid = length / 2;
        Node left = mergeKSortedList(nodes, start, mid);
        Node right = mergeKSortedList(nodes, mid + 1, end);
        return mergeSorted(right, left);
    }

    private static Node mergeSorted_itr(Node one, Node two) {
        if (one == null) return two;
        if (two == null) return one;
        if (one == null && two == null) return null;
        Node ret = null;
        if (one.data < two.data) {
            ret = one;
            one = one.next;
        } else {
            ret = two;
            two = two.next;
        }
        Node tmp = ret;
        while(one!=null && two!=null) {
            if (one.data < two.data) {
                tmp.next = one;
                one = one.next;
            } else {
                tmp.next = two;
                two = two.next;
            }
            tmp = tmp.next;
        }
        if (one !=null) tmp.next = one;
        if (two !=null) tmp.next = two;
        return ret;
    }

}
