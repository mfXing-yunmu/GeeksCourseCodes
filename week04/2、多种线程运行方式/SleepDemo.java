package com.yunmu.geek;

/**
 * @author mfXing
 */
public class SleepDemo {
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
        new Thread(() -> result = getStr()).start();

        /** 主线程使用 Sleep 方法，延时 20ms>10ms，保证子线程运行完毕 */
        try {
            System.out.println("运行结果(before)：result = " + result);
            Thread.sleep(20);
            System.out.println("运行结果(after)：result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
