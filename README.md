# MultiThreading

* CPU CACHEs - L1, l2 and L3 caches
* use lscpu on Unix to see L1, L2 Caches
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
   * Set uncaughtExeceptionHandler
   * Must wrap checked Exceptions as these can't be thrown from run

* How the JVM handles Uncaught Exceptions from Threads
   * When a thread throws uncaught unchecked exception, threads's dispatchUncaughtException() is called by the JVM. Uses uncaughtExceptionHandler to determine what to call
   * we can set a Custom Exception Handler by calling setUncaughtExceptionHandler() and passing an instance of a class which implements UncaughtExceptionHandler
   * Read about thread group (virtually decreptated). ExecutionException also
 * ThreadLocal - Data persists till thread dies. Use threadLocal.remove();
 * Thread stop, suspend, resume - deprecated. [Why?](https://www.javamadesoeasy.com/2015/03/reason-why-suspend-and-resume-methods.html) Use interuppt instead.
 
