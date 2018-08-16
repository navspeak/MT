package com.sharingMemory.producerConsumer.unsynchronized;

import java.util.List;
/*
When we construct an object, as long as we don't publish it while it is being constructed,
i.e. allow "this" to escape, it is guaranteed that other threads will only see initialised final values.
Thus saving "this" reference in static variables during construction is a bad idea
 */
public class LeakingThis {
    public final Object obj1;
    public static volatile List<Object> listThatWillHelpLeak;

    LeakingThis(){
        obj1 = new Object();
        this.listThatWillHelpLeak.add(obj1);
        this.listThatWillHelpLeak.add(this);
    }
}
