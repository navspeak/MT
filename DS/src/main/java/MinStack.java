import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinStack {
    List <Integer> minimumstack;
    List <Integer> actualstack;
    int size = 0;
    MinStack() {
        minimumstack = new ArrayList<>();
        actualstack = new ArrayList<>();
    }

    public Integer getSize() {
        return size;
    }

    public static void main(String[] args) {
        MinStack minstack = new MinStack();
        System.out.println();
        minstack.push(10);
        System.out.println("===========");
        System.out.println(minstack.top() == 10);
        System.out.println(minstack.getMin() == 10);
        minstack.push(2);
        System.out.println("===========");
        System.out.println(minstack.top() == 2);
        System.out.println(minstack.getMin() == 2);
        minstack.push(11);
        System.out.println("===========");
        System.out.println(minstack.top() == 11);
        System.out.println(minstack.getMin() == 2);
        minstack.push(1);
        System.out.println("===========");
        System.out.println(minstack.top() == 1);
        System.out.println(minstack.getMin() == 1);
        minstack.push(12);
        System.out.println("===========");
        System.out.println(minstack.top() == 12);
        System.out.println(minstack.getMin() == 1);
        minstack.push(3);
        System.out.println("===========");
        System.out.println(minstack.top() == 3);
        System.out.println(minstack.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 3);
        System.out.println(minstack.top() == 12);
        System.out.println(minstack.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 12);
        System.out.println(minstack.top() == 1);
        System.out.println(minstack.getMin() == 1);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 1);
        System.out.println(minstack.top() == 11);
        System.out.println(minstack.getMin() == 2);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 11);
        System.out.println(minstack.top() == 2);
        System.out.println(minstack.getMin() == 2);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 2);
        System.out.println(minstack.top() == 10);
        System.out.println(minstack.getMin() == 10);
        System.out.println("=====-----------======");
        System.out.println(minstack.pop() == 10);
        System.out.println(minstack.top() == null);
        System.out.println(minstack.getMin() == -1);
    }

    public Integer pop() {
        if (size == 0) return null;
        Integer ret = actualstack.remove(size -1);
        minimumstack.remove(size -1);
        size--;
        return ret;
    }
    public Integer top() {
        if (size == 0) return null;
        return actualstack.get(size - 1);
    }
    public Integer getMin() {
        if (size == 0) return -1;
        return minimumstack.get(size - 1);
    }

    public void push(int num) {
        actualstack.add(num);
        if (size == 0 || num < getMin()) {
            minimumstack.add(num);
            System.out.println();
        }
        else {
            minimumstack.add(getMin());;
        }
        size++;
    }
}
