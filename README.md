# MultiThreading

* CPU CACHEs - L1, l2 and L3 caches
* use lscpu on Unix or WMIC CPU Get /Format:List on windows to see L1, L2 Caches
* Creating threads:
   * Extending Thread class | implement Runnable (preferred)
   * Thread also extends Runnable
   * Standard Thread persists until exiting run() exception Daemon thread
* Daemon Thread 
   * Thread.setDaemon() before start()
   * Dies anytime when no  daemon thread exits w/o clean up, no finalizer call.Shouldn't use for resources that require closing. 
    * Used for support threads like GC thread
* Thread FSM : 
   * NEW  -> Runnable -> Terminated 
   * NEW - start() is called. Calling start() twice throws illegal Thread State exception
   * getState() returns Thread.State enum
* Sleep 
   * static method on Thread. Causes thread to go into Timed waiting state
      
         NEW -> Runnable -> Terminted
                |    |
              Timed-waiting
   * Sleep takes nano seconds but it rounds to millisecond
   * Sleep(0) => platform dependent, may or may not sleep
   * yield - avoid. Thread.setPriority() - use with caution, can cause starvation if scheduler gives too much time to this thread
 
 * jstack | jps | jconsole
 * Interrupted Exception 
   *  U may call interrupt() on a sleeping thread instance and it will throw interrupted ex. Also, wait(). Checked Exception.
    * What happens when thread is not sleeping?
       - We can call interrupted() that checks for & clears the interrupt. Static methods, can't call on other threads as that will be disastrous to clear other threads interrupt
       - isInterupted() checks interupt but doesn't clear. 
       - isAlive() can be used to check if thread is alive or may have exited a timed join
* Catching Exceptions Thrown from run()
   * JVM calls dispatchUncaughtException() on the thread object when run throws a runtime exception
   * Set uncaughtExeceptionHandler (UncaughtExceptionHandler class with an ExecutionException member)
   * Must wrap checked Exceptions as these can't be thrown from run

* How the JVM handles Uncaught Exceptions from Threads
   * When a thread throws uncaught unchecked exception, threads's dispatchUncaughtException() is called by the JVM. Uses uncaughtExceptionHandler to determine what to call
   * we can set a Custom Exception Handler by calling setUncaughtExceptionHandler() and passing an instance of a class which implements UncaughtExceptionHandler
   * Read about thread group (virtually decreptated). ExecutionException also.  [Code](https://gist.github.com/navspeak/38030afc47c7648f05236f4d0adbba24)
 * ThreadLocal - Data persists till thread dies. Use threadLocal.remove();
 * Thread stop, suspend, resume - deprecated. [Why?](https://www.javamadesoeasy.com/2015/03/reason-why-suspend-and-resume-methods.html) Use interuppt instead.
 
 # Sharing Memory across threads
 * Are finals safe? Yes (though not in case where _this_ escapes the constructor). Publishing immutable data removes inconsistency
 * [Java Memory Model](https://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html)
 * Volatile - Visibility Guarantee and [Happens-before Guarantee](http://tutorials.jenkov.com/java-concurrency/volatile.html#the-java-volatile-happens-before-guarantee) 
 * [Single write to non volatile long and double primitive is not done atomically](https://dzone.com/articles/longdouble-are-not-atomic-in-java)
 
 * Deadlock Prevention Strategy (no thread makes progress. Directed Cyclic Graph. Use Jstack):
    * Always aquire mutexes in same order
    * If possible replace two mutexes with one
    * try-lock : not supported by synchronized though (shouldn't be first choice though, can lead it livelock also)
 * Livelock
 * Starvation
 
 # Wait, Notify, NotifyAll
 * Object is associated with a wait set along with a monition. Each object has a wait() method:
    * Calling wait without synchronizing the object throws IllegalMonitorException
    * For a thread to wait for a signal requires a condition variable aka wait set. This queues the thread waiting for the condition.
    * Join works by waiting on a thread instance. Thus we should not use wait on thread directly.
    * Wait causes the thread to be added to the wait set, release the monitor. Other threads can call notify or notifyAll
    * Notifying thread must release the monitor as soon as it can, otherwise __Starvation__ cud occur coz awakened thread can't acquire the monitor
    * NotifyAll() notifies & wakes all the thread, though only one may acquire the monitor. 
        * This can cause performance issue if there's a large no. of thread.
        * However, like in case of multiple producers and consumers, you need to notify more than one thread at a time so use notifyAll
    * Spurious Wakeup - wake from wait without getting notified. 
    
* [Never use stop() to stop a thread.](https://stackoverflow.com/questions/16504140/thread-stop-deprecated)
* [False sharing](https://medium.com/@rukavitsya/what-is-false-sharing-and-how-jvm-prevents-it-82a4ed27da84)
* Reentrant Lock uses Bridge pattern  