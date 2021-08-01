package com.basic;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class ThreadTest {
    @Data
    class Object1 {
        ThreadLocal<Object2> tl = new ThreadLocal();
    }

    @Data
    class Object2 {
        private byte[] memory;
    }

    @Test
    public void pool1() throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("core size: " + cores);

        ExecutorService fixedThreadPool = new ThreadPoolExecutor(3, 5,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            fixedThreadPool.execute(new Thread(() -> {
//                Byte[] bytes = new Byte[1024 * 1000 * 1000];
                System.out.println(Thread.currentThread() + ": " + finalI);
            }));
        }
        // 所有任务执行完成且等待队列中也无任务关闭线程池
        fixedThreadPool.shutdown();
        // 阻塞主线程, 直至线程池关闭
        fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        Thread.sleep(200000);
    }

    @Test
    public void pool2() throws InterruptedException {
        List<String> strs = new ArrayList<>();
        LocalDateTime loopStartTime = LocalDateTime.now();
        for (int i = 0; i < 10; i++) {
            strs.add(Integer.toString(i + 1));
            Thread.sleep(100);
        }
        LocalDateTime loopEndTime = LocalDateTime.now();
        System.out.println("loop timeout: " + Duration.between(loopStartTime, loopEndTime).toMillis());
        ExecutorService pool = new ThreadPoolExecutor(12, 12,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        LocalDateTime concurrentStartTime = LocalDateTime.now();

        List<CompletableFuture<String>> ps = strs.stream().map(t -> CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
            return t;
        }, pool)).collect(Collectors.toList());
        var ret = ps.stream().map(CompletableFuture::join).collect(Collectors.toList());
        LocalDateTime concurrentEndTime = LocalDateTime.now();
        System.out.println("concurrent timeout: " + Duration.between(concurrentStartTime, concurrentEndTime).toMillis());
        System.out.println(ret);
    }

    @Test
    public void threadLocal1() {
        Object1 o1 = new Object1();
        Object2 o2 = new Object2();
        o2.setMemory(new byte[1024 * 1024*1024]);
        o1.getTl().set(o2);
        o1.getTl().remove();
        System.out.println("ff");
    }
}