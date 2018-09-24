package linkedList;

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
        Node flattenedList = head;
        for (Node ptr = head; ptr!=null; ptr = ptr.next){
            if (ptr.down !=null) {
                flattenedList = merge(flattenedList, ptr.down);
            }
            ptr = ptr.next;
        }
        print(flattenedList);
    }

    private static Node merge(Node one, Node two) {
        if (one == null) return two;
        if (two == null) return one;

        Node tmp = null;
        if (one.data < two.data){
            tmp = one;
            tmp.next = merge(one.next, two);
        } else {
            tmp = two;
            tmp.next = merge(one, two.next);
        }
        return tmp;
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
            return ""+data;
        }
    }

    public static void print(Node node){
        for (Node ptr = node; ptr!=null; ptr = ptr.next){
            System.out.print(ptr.data+"->");
        }
        System.out.print("NULL\n");
    }

}
