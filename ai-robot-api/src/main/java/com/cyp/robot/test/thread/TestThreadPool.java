package com.cyp.robot.test.thread;


import java.util.concurrent.*;

public class TestThreadPool {

    public static ExecutorService executorService02;
    public static ExecutorService executorService03;

    static {
        executorService02 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        executorService03 = new ThreadPoolExecutor(
                100,
                1000,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000));


        new ThreadPoolExecutor(
                100,
                1000,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return null;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                }
        );
    }

    public static void main(String[] args) {
        System.out.println("isDone: " + Runtime.getRuntime().availableProcessors());
    }


}
