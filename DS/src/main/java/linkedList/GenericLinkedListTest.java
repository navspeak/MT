package linkedList;

import java.util.Iterator;

public class GenericLinkedListTest {
    public static void main(String[] args) {

        //Sorting Test
        GenericLinkedList<Integer> list =
                new GenericLinkedList<>(8,1,9,7,6,5);
        System.out.println(list);
        System.out.println(list.sort());

        // String list
        GenericLinkedList<String> stringList =
                new GenericLinkedList<>("apple", "guava", "kiwi", "peach", "banana");
        stringList.addToEnd("mango");
        stringList.addAtBeginning("pear");
        System.out.println("List of strings : " + stringList);               //pear->apple->guava->kiwi->peach->banana->Mango->NULL
        System.out.println("List Reversed : " + stringList.reverse());     // Mango->banana->peach->kiwi->guava->apple->pear->NULL
        System.out.println("Sorted List : " + stringList.sort());
        System.out.println("Original List : " + stringList);
        //Iterator testing
        Iterator<String> itr = stringList.iterator();
//        while(itr.hasNext()){
//            //Throw Concurrent Modification Exception
//            stringList.addAtBeginning("Test");
//        }
        //==============
        GenericLinkedList<Person> personList =
                new GenericLinkedList<>(new Person("Ram", 34),
                        new Person("Sam", 38));
        personList.addAtBeginning(new Person("Foo", 28));
        personList.addToEnd(new Person("Bar", 36));
        System.out.println(personList);
        //Person{age=28, name='Foo'}->Person{age=34, name='Ram'}->Person{age=38, name='Sam'}->Person{age=36, name='Bar'}->NULL
        //


    }
}

class Person implements Comparable<Person> {
    int age; String name;
    Person(String name, int age){
        this.name = name; this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person anotherPerson) { //https://medium.com/omarelgabrys-blog/comparing-objects-307400115f88
        if (this.age < anotherPerson.age) return -1;
        else if (this.age > anotherPerson.age) return 1;
        else {
            // if age is same, compare name lexically
            return name.compareTo(anotherPerson.name);
        }
    }
}