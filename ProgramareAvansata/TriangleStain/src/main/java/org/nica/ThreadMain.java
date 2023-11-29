package org.nica;

import static java.time.LocalDateTime.now;


class RunnableA implements Runnable
{
    RunnableA(){}
    @Override
    public void run() {
        System.out.println ("Runnable A Start");
        try {
            System.out.println ("Runnable A sleep 1");
            Thread.sleep(1000);
            System.out.println ("Runnable A slept 1, before notifyAll");
            synchronized (this) { notifyAll(); }
            System.out.println ("Runnable A sleep 2 after notifyAll");
            Thread.sleep(1000);
            System.out.println ("Runnable A slept 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println ("Runnable A End");
    }
}
class ThreadA extends Thread
{
    Thread cls = new ThreadB();;
    ThreadA() {}
    public void run()
    {
        System.out.println ("Thread A Start");
        cls.start();
        try {
            System.out.println ("Thread A before wait");
            synchronized (cls) { cls.wait(); }
            System.out.println ("Thread A after wait");
        } catch (InterruptedException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        System.out.println ("Thread A End");
    }
}
class ThreadB extends Thread
{
    RunnableA clr = new RunnableA();
    ThreadB(){}
    public void run()
    {
        new Thread(clr).start();
        System.out.println ("Thread B Start");
        try {
            synchronized(clr) {clr.wait();}
            System.out.println ("Thread B sleep 1");
            Thread.sleep(2000);
            System.out.println ("Thread B slept 1, before notifyAll");
            synchronized (this) { notifyAll(); }
            System.out.println ("Thread B sleep 2 after notifyAll");
            Thread.sleep(2000);
            System.out.println ("Thread B slept 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println ("Thread B End");
    }
}
public class ThreadMain {


    public static void main(String[] args) throws Exception {
        Thread x = new ThreadA();
        x.start();
        System.out.println("Main thread before join");
        x.join();
        System.out.println("Main thread after join");
    }
}
