package com.basic;

import com.basic.model.Compute;
import org.junit.Test;

import java.util.concurrent.*;

public class ThreadTest {

    class Task implements Callable<Integer> {
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