package com.yunmu.geek;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mfXing
 */
public class FutureGetDemo {
    /** 初始化运行结果 */
    public static String result = "Init";

    public static String getStr(){
        /** 模拟方法运行过程 Sleep 10ms */
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /** 模拟方法运行结果 */
        return "success";
    }

    public static void main(String[] args) {
        /** 创建一个固定大小的线程池 */
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future future = executor.submit(() -> {
            result = getStr();
        });

        /** 主线程调用 future.get() 方法阻塞，等待所有子线程都执行结束，再执行主线程 */
        try {
            System.out.println("运行结果(before)：result = " + result);
            future.get();
            System.out.println("运行结果(after)：result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
