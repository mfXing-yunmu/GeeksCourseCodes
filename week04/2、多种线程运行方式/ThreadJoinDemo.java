package com.yunmu.geek;

/**
 * @author mfXing
 */
public class ThreadJoinDemo {
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
        Thread thread = new Thread(() -> result = getStr());
        thread.start();

        /** 主线程使用 thread.join() 方法，等待所有子线程执行完，主线程再执行 */
        try {
            System.out.println("运行结果(before)：result = " + result);
            thread.join();
            System.out.println("运行结果(after)：result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
