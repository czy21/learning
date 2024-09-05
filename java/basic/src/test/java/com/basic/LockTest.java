package com.basic;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class LockTest {

    @Data
    static class Object1 {
        private int count;
        private AtomicInteger atomicCount = new AtomicInteger();
        private ReentrantLock reentrantLock = new ReentrantLock(true);

        public synchronized void syncIncrease() {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            count++;
        }

        public synchronized void syncABySynchornized(String value) {
            System.out.println(StringUtils.join(List.of("syncA synchorized thread:", Thread.currentThread(), "value:", value), " "));
            syncBBySynchornized(value);
        }

        public synchronized void syncBBySynchornized(String value) {
            System.out.println(StringUtils.join(List.of("syncB synchorized thread:", Thread.currentThread(), "value:", value), " "));
        }

        public void syncAByReentrantLock(String value) {
            System.out.println(StringUtils.join(List.of("1. syncA enter  ", Thread.currentThread().getName(), "value:", value), " "));
            reentrantLock.lock();
            System.out.println(StringUtils.join(List.of("2. syncA lock   ", Thread.currentThread().getName(), "value:", value), " "));
            syncBByReentrantLock(value);
            reentrantLock.unlock();
            System.out.println(StringUtils.join(List.of("3. syncA unlock ", Thread.currentThread().getName(), "value:", value), " "));
        }

        public void syncBByReentrantLock(String value) {
            reentrantLock.lock();
            System.out.println(StringUtils.join(List.of("4. syncB lock   ", Thread.currentThread().getName(), "value:", value), " "));
            reentrantLock.unlock();
        }

        public void casIncrease() {
            atomicCount.getAndIncrement();
        }

    }

    /*
     * 使用synchronized解决多线程操作导致得线程不安全 (悲观锁) 一条线程执行,其他线程排队
     */
    @SneakyThrows
    @Test
    public void test1() {
        Object1 o1 = new Object1();
        int loopSize = 1000;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < loopSize; i++) {
            threads.add(new Thread(o1::syncIncrease));
        }
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
        Assertions.assertEquals(loopSize, increaseValue);
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

    /*
     * synchornized 可重入锁验证 (非公平锁)
     */
    @Test
    public void test3() {
        Object1 o1 = new Object1();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> o1.syncABySynchornized(Integer.toString(finalI))).start();
        }
    }

    /*
     * ReentrantLock 可重入锁验证 (非公平锁)
     */
    @Test
    public void test4() {
        Object1 o1 = new Object1();
        for (int i = 0; i < 10; i++) {
            int finalI = i + 1;
            new Thread(() -> o1.syncAByReentrantLock(Integer.toString(finalI)), "thread-" + finalI).start();
        }
    }

}
