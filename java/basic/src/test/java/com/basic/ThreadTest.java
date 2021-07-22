package com.basic;

import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class ThreadTest {

    @Data
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
    @SneakyThrows
    @Test
    public void test1() {
        Object1 o1 = new Object1();
        int loopSize = 1000;
        List<Thread> threads = List.of(
                getThread(o1, loopSize, Object1::syncIncrease),
                getThread(o1, loopSize, Object1::syncIncrease)
        );

        LocalDateTime startTime = LocalDateTime.now();
        threads.forEach(Thread::start);
        for (var t : threads) {
            t.join();
        }
        LocalDateTime endTime = LocalDateTime.now();
        int increaseValue = o1.getCount();
        System.out.println("loop size: " + loopSize);
        System.out.println("increase value: " + increaseValue);
        System.out.println("threads: " + threads.size());
        System.out.println("timeout: " + Duration.between(startTime, endTime).toMillis() + "ms");
        Assertions.assertEquals(loopSize * threads.size(), increaseValue);
    }

    private Thread getThread(Object1 t, int loopSize, Consumer<Object1> func) {
        return new Thread(() -> {
            for (int i = 0; i < loopSize; i++) {
                func.accept(t);
            }
        });
    }

    /*
     * 使用cas解决多线程操作导致得线程不安全
     */
    @SneakyThrows
    @Test
    public void test2() {
        Object1 o1 = new Object1();
        int loopSize = 1000;
        List<Thread> threads = List.of(
                getThread(o1, loopSize, Object1::casIncrease),
                getThread(o1, loopSize, Object1::casIncrease)
        );

        LocalDateTime startTime = LocalDateTime.now();
        threads.forEach(Thread::start);
        for (var t : threads) {
            t.join();
        }
        LocalDateTime endTime = LocalDateTime.now();
        int increaseValue = o1.getAtomicCount().get();
        System.out.println("loop size: " + loopSize);
        System.out.println("increase value: " + increaseValue);
        System.out.println("threads: " + threads.size());
        System.out.println("timeout: " + Duration.between(startTime, endTime).toMillis() + "ms");
        Assertions.assertEquals(loopSize * threads.size(), increaseValue);
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

    @Test
    public void pool1() throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("core size: " + cores);
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            fixedThreadPool.execute(new Thread(() -> {
                Byte[] bytes = new Byte[1024 * 1000 * 1000];
                System.out.println(finalI);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
        // 所有任务执行完成且等待队列中也无任务关闭线程池
        fixedThreadPool.shutdown();
        // 阻塞主线程, 直至线程池关闭
        fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Thread.sleep(200000);
    }

}