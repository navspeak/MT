package com.threadLocalNeed;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DateFormatterExampleThreadLocal {
    public static Set<SimpleDateFormat> dateSet = new HashSet<>();
    public static class DateFormatterThreadLocal extends ThreadLocal<SimpleDateFormat> {
        DateFormatterThreadLocal() {
            System.out.println("Ctor of threadLocal Obj");
        }
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d, hh:mm:ss");
            dateSet.add(simpleDateFormat);
            System.out.println(Thread.currentThread().getName() + " has the SimpleDateFormat with ObjectID" + simpleDateFormat);
            return simpleDateFormat;
        }

        @Override
        public SimpleDateFormat get() {
            SimpleDateFormat simpleDateFormat = super.get();
            System.out.println(Thread.currentThread().getName() + " is Returning " + simpleDateFormat);
            return simpleDateFormat;
        }
    }
 //   public static SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM d, hh:mm:ss");
    public static DateFormatterThreadLocal dateFormatterVar = new DateFormatterThreadLocal();

    public static void main(String[] args) {
        Thread t1 = new Thread(new DatePrinter("Formatter 1"), "Formatter 1");
        Thread t2 = new Thread(new DatePrinter("Formatter 2"), "Formatter 2");

        t1.start();
        t2.start();
    }

    public static class DatePrinter implements Runnable{
        private String name;

        public DatePrinter(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            if (name.equals("Formatter 1")) {
                SimpleDateFormat simpleDateFormat = dateFormatterVar.get();
                System.out.println(name + " is setting the formatting pattern for SimpleDateFormat object  " + simpleDateFormat);
                simpleDateFormat.applyPattern("hh:mm:ss");
            }

            while (true) {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e)
                {
                    // Can ignore
                }

                Date now = new Date();
                System.out.println(name + ": " + dateFormatterVar.get().format(now));
                System.out.println("=============");
                dateSet.forEach(System.out::println);
                System.out.println("=============");
            }
        }
    }
}

