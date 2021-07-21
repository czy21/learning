package com.basic;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadTest {

    static class Task implements Callable<Integer> {
        private Integer taskID;

        public Task(Integer taskID) {
            this.taskID = taskID;
        }

        public Integer call() throws Exception {
//            if(taskID.equals(3))
//                Thread.sleep(10000);
            System.out.println("任务[" + taskID + "]开始执行");
            return taskID;
        }
    }

    @Data
    static class Object1 {
        private int count;
        private AtomicInteger atomicCount = new AtomicInteger();

        public synchronized void syncIncrease() {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }

        public void casIncrease() {
            atomicCount.getAndIncrement();
        }

    }

    /*
     * 使用synchronized解决多线程操作导致得线程不安全
     */
    @Test
    public void test1() {
        Object1 t = new Object1();
        List<Thread> threads = new ArrayList<>();
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(t::syncIncrease));
        }
        threads.forEach(Thread::start);
        threads.forEach(p -> {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Assertions.assertEquals(threadCount, t.getCount());
    }

    /*
     * 使用cas解决多线程操作导致得线程不安全
     */
    @Test
    public void test2() {
        Object1 t = new Object1();
        List<Thread> threads = new ArrayList<>();
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(t::casIncrease));
        }
        threads.forEach(Thread::start);
        threads.forEach(p -> {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Assertions.assertEquals(threadCount, t.getAtomicCount().get());
    }

    @Test
    public void single() {
        //创建一个线程池
        ExecutorService pools = Executors.newFixedThreadPool(5);
        CompletionService<Integer> s = new ExecutorCompletionService<>(pools);
        //创建多个有返回值的任务
        for (int i = 0; i <= 10; i++) {
            s.submit(new Task(i));
        }
        for (int i = 0; i <= 10; i++) {
            try {
                Future<Integer> f = s.take();
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        pools.shutdown();

    }


}