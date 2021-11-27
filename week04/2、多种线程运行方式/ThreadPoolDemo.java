package com.yunmu.geek;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mfXing
 */
public class ThreadPoolDemo {
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
        executor.submit(() -> {
            result = getStr();
        });
        executor.shutdown();

        System.out.println("运行结果(before)：result = " + result);
        /** 等待所有子线程都执行结束 */
        while (true){
            /** 所有子线程都执行结束了 */
            if(executor.isTerminated()){
                break;
            }
        }
        System.out.println("运行结果(after)：result = " + result);
    }
}
