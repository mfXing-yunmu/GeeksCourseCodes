package com.yunmu.geek;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author mfXing
 */
public class CyclicBarrierDemo {
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
        /** 启动一个新线程并运行 */
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> result = getStr());

        /** 主线程使用 cyclicBarrier.await() 方法，当所有子线程都到达屏障点再执行主线程 */
        try {
            System.out.println("运行结果(before)：result = " + result);
            cyclicBarrier.await();
            System.out.println("运行结果(after)：result = " + result);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
