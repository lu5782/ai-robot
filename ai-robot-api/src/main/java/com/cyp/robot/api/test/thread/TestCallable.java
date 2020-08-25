package com.cyp.robot.api.test.thread;

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
        return "--------TestCallable----------";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable testCallable = new TestCallable();
        FutureTask futureTask = new FutureTask<>(testCallable);
        new Thread(futureTask,"futureTask123").start();
        boolean done = futureTask.isDone();
        System.out.println(futureTask.isDone()+"success:"+futureTask.get());
        if(done){
            System.out.println(futureTask.isDone()+"success:"+futureTask.get());
        }
    }
}
