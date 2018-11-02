import java.util.LinkedList;

public class MinStackImproved {
    private class Value {
        int val;
        int min;
    }

    LinkedList <Value> stack;
    int size = 0;
    MinStackImproved() {
        stack = new LinkedList<>();
    }

    public Integer getSize() {
        return stack.size();
    }

    public Integer pop() {
        if (getSize() == 0) return -1;
        return stack.pop().val;
    }

    public Integer top() {
        if (getSize() == 0) return -1;
        return stack.peek().val;
    }
    public int getMin() {
        if (getSize() == 0) return -1;
        return stack.peek().min;
    }

    public void push(int num) {
       Value val = new Value();
       val.val = num;
        final int minTillNow = stack.size() == 0 ? -1 : stack.peek().min;
        if (stack.size() == 0){
           val.min = num;
       } else {
           if (num < minTillNow)
               val.min = num;
           else
               val.min = minTillNow;
       }
       stack.push(val);
    }


    public static void main(String[] args) {

//19 P 10 P 9 g P 8 g
// P 7 g P 6 g p g p g p g p g p g
        MinStackImproved minStackImproved = new MinStackImproved();
//        minStackImproved.push(10);
//        minStackImproved.push(9);
//        System.out.println(minStackImproved.getMin());
//        minStackImproved.push(8);
//        System.out.println(minStackImproved.getMin());
        //MinStackImproved minStackImproved = new MinStackImproved();
        minStackImproved.push(10);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 10);
        System.out.println(minStackImproved.getMin() == 10);
        minStackImproved.push(2);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 2);
        System.out.println(minStackImproved.getMin() == 2);
        minStackImproved.push(11);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 11);
        System.out.println(minStackImproved.getMin() == 2);
        minStackImproved.push(1);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 1);
        System.out.println(minStackImproved.getMin() == 1);
        minStackImproved.push(12);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 12);
        System.out.println(minStackImproved.getMin() == 1);
        minStackImproved.push(3);
        System.out.println("===========");
        System.out.println(minStackImproved.top() == 3);
        System.out.println(minStackImproved.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 3);
        System.out.println(minStackImproved.top() == 12);
        System.out.println(minStackImproved.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 12);
        System.out.println(minStackImproved.top() == 1);
        System.out.println(minStackImproved.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 1);
        System.out.println(minStackImproved.top() == 11);
        System.out.println(minStackImproved.getMin() == 2);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 11);
        System.out.println(minStackImproved.top() == 2);
        System.out.println(minStackImproved.getMin() == 2);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 2);
        System.out.println(minStackImproved.top() == 10);
        System.out.println(minStackImproved.getMin() == 10);
        System.out.println("=====-----------======");
        System.out.println(minStackImproved.pop() == 10);
        System.out.println(minStackImproved.top() == -1);
        System.out.println(minStackImproved.getMin() == -1);
    }

}
