package com.cyp.robot.api.test.thread;

import java.util.concurrent.*;

public class TestThreadPool {

    public static ExecutorService executorService;
    public static ExecutorService executorService02;
    public static ExecutorService executorService03;

    static{
        executorService = Executors.newFixedThreadPool(3);
        executorService02 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        executorService03 = new ThreadPoolExecutor(100,1000,2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000));
    }

    public static void main(String[] args) {
        System.out.println("isDone: "+ Runtime.getRuntime());
        System.out.println("isDone: "+ Runtime.getRuntime().availableProcessors());
    }



}
