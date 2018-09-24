package linkedList;
//https://www.geeksforgeeks.org/flattening-a-linked-list/
public class FlattenALinkedList {
    public static void main(String[] args) {
        /* Let us create the following linked list
       5 -> 10 -> 19 -> 28
       |    |     |     |
       V    V     V     V
       7    20    22    35
       |          |     |
       V          V     V
       8          50    40
       |                |
       V                V
       30               45
    */
        Node head = createSampleMultiLevelList();
       // Node flattenedList = flatten_rec(head);
        Node flattenedList = flatten_itr(head);
        print(flattenedList);
    }

    private static Node flatten_rec(Node head) {
        if (head == null || head.next == null) return head;
        return merge_rec(head, flatten_rec(head.next));
    }

    private static Node merge_rec(Node one, Node two) {
        if (one == null) return two;
        if (two == null) return one;

        Node tmp = null;
        if (one.data < two.data){
            tmp = one;
            tmp.down = merge_rec(one.down, two);
        } else {
            tmp = two;
            tmp.down = merge_rec(one, two.down);
        }
        return tmp;
    }


    private static Node flatten_itr(Node head) {
        Node flattenedList = head;
        for (Node ptr = head; ptr!=null; ptr = ptr.next){
            flattenedList = merge_itr(flattenedList, ptr.next);
        }
        return flattenedList;
    }
    private static Node merge_itr(Node one, Node two) {
        if (one == null) return two;
        if (two == null) return one;
        if (one == null && two == null) return null;
        Node ret = null;
        if (one.data < two.data) {
            ret = one;
            one = one.down;
        } else {
            ret = two;
            two = two.down;
        }
        Node tmp = ret;
        while(one!=null && two!=null) {
            if (one.data < two.data) {
                tmp.down = one;
                one = one.down;
            } else {
                tmp.down = two;
                two = two.down;
            }
            tmp = tmp.down;
        }
        if (one !=null) tmp.down = one;
        if (two !=null) tmp.down = two;
        return ret;
    }

    private static Node createSampleMultiLevelList() {
        Node five = new Node(5);
        Node seven = new Node(7);
        Node eight = new Node(8);
        Node thirty = new Node(30);
        five.down = seven;
        seven.down=eight;
        eight.down = thirty;

        Node ten = new Node(10);
        Node twenty = new Node(20);
        ten.down = twenty;

        Node nineteen = new Node(19);
        Node twentytwo = new Node(22);
        Node fifty = new Node(50);
        nineteen.down = twentytwo;
        twentytwo.down=fifty;

        Node twentyeight = new Node(28);
        Node thirtyfive = new Node(35);
        Node forty = new Node(40);
        Node fortyfive = new Node(45);
        twentyeight.down = thirtyfive;
        thirtyfive.down=forty;
        forty.down = fortyfive;

        five.next = ten;
        ten.next = nineteen;
        nineteen.next = twentyeight;
        return five;
    }

    static class Node {
        int data;
        Node next, down;
        Node(int data){ this.data = data;}

        @Override
        public String toString() {
            return data+"";
//            if (this.next == null && this.down == null)
//                return data+"->Null";
//            else {
//                StringBuilder sb = new StringBuilder();
//                sb.append("  [Across] ");
//                for (Node ptr = this; ptr != null; ptr = ptr.next) {
//                    sb.append(ptr.data).append("->");
//                }
//                sb.append("Null").append(" [Down]  ");
//                for (Node ptr = this; ptr != null; ptr = ptr.down) {
//                    sb.append(ptr.data).append("->");
//                }
//                sb.append("Null");
//                return sb.toString();
//            }
        }
    }

    public static void print(Node node){
        for (Node ptr = node; ptr!=null; ptr = ptr.down){
            System.out.print(ptr.data+"->");
        }
        System.out.print("NULL\n");
    }

}
