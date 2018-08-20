package com.one;

public class Singleton {
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }

    private Singleton(){}
    private static class SingletonHolder{
        private static Singleton instance = new Singleton();
    }
}
//This looks awfully clever -- the synchronization is avoided on the common code path.
// There's only one problem with it -- it doesn't work.
// Why not? The most obvious reason is that:
// 1. the writes which initialize instance
// 2. the write to the instance field
// can be reordered by the compiler or the cache, which would have the effect of returning what
// appears to be a partially constructed Something.
// The result would be that we read an uninitialized object.
// There are lots of other reasons why this is wrong, and why algorithmic corrections to it are wrong.
// There is no way to fix it using the old Java memory model.

// double-checked-locking - don't do this!
//
//    private static Something instance = null;
//
//    public Something getInstance() {
//        if (instance == null) {
//            synchronized (this) {
//                if (instance == null)
//                    instance = new Something();
//            }
//        }
//        return instance;
//    }