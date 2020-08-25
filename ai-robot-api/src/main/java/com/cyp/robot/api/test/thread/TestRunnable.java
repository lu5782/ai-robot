package com.cyp.robot.api.test.thread;


public class TestRunnable implements Runnable {

    public String data;

    public TestRunnable(String data) {
        this.data = data;
    }


    @Override
    public void run() {
//        TestThread testThread = new TestThread();
//        //单独调用run()方法，是同步执行；通过start()调用run()，是异步执行。
//        testThread.start();
//        testThread.run();
//
//        TestRunnable testRunnable = new TestRunnable();
//        testRunnable.run();
//        new Thread(testRunnable).start();
//        new Thread(testRunnable).run();

//        System.out.println("---------TestRunnable begin----------");
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        long id = thread.getId();
        Thread.State state = thread.getState();
        boolean alive = thread.isAlive();
        System.out.println(name + "," + id + "," + state + "," + alive + "," + data);
        String[] split = data.split(",");
        if (split.length != 6) {
            System.out.println("------------------------- error ----------------------------");
        } else {
            String[] split1 = split[0].split(":");
            Integer integer = Integer.valueOf(split1[2]);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("---------TestRunnable end----------");
    }
}
