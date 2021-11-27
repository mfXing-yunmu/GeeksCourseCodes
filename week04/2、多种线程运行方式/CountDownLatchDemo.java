package com.yunmu.geek;

import java.util.concurrent.CountDownLatch;

/**
 * @author mfXing
 */
public class CountDownLatchDemo {
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
        /** 初始化计数器为子线程数量 */
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        /** 启动一个新线程并运行 */
        new Thread(() -> {
            result = getStr();
            /** 子线程完成自己的任务后，计数器的值减一 */
            countDownLatch.countDown();
        }).start();

        /** 主线程使用 countDownLatch.await() 方法，当计数器的值变为 0 时，主线程就会被唤醒 */
        try {
            System.out.println("运行结果(before)：result = " + result);
            countDownLatch.await();
            System.out.println("运行结果(after)：result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
