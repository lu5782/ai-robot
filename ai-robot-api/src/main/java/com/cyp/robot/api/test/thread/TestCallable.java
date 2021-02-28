package com.cyp.robot.api.test.thread;

import java.sql.Timestamp;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable 、 FutureTask使用
 */
public class TestCallable implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println("TestCallable 方法执行了");
        Thread.sleep(5000);
        return "--------TestCallable----------";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable testCallable = new TestCallable();
        FutureTask futureTask = new FutureTask<>(testCallable);
        new Thread(futureTask, "futureTask123").start();
        boolean done = futureTask.isDone();
        while (!done) {
            done = futureTask.isDone();
            System.out.println(new Timestamp(System.currentTimeMillis()) + "done = " + done);
            Thread.sleep(1000);
        }
        System.out.println(new Timestamp(System.currentTimeMillis()) + "success:" + futureTask.get());
    }
}
