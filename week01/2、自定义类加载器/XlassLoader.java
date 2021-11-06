package com.yunmu.geek;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 */
public class XlassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            // 创建自定义类加载器
            XlassLoader xlassLoader = new XlassLoader();

            // 加载 Hello.xlass
            Class<?> loadClass = xlassLoader.loadClass("Hello");

            // 新生成一个实例对象
            Object newInstance = loadClass.newInstance();

            // 执行 Hello.xlass 中的 hello 方法
            Method method = loadClass.getMethod("hello");
            method.invoke(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        try {
            // 读取 src/main/resources/ 目录下的 Hello.xlass 文件
            File file = new File("src/main/resources/Hello.xlass");

            // 读取输入的数据流
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);

            // 根据已知的加密规则进行解密
            byte[] decryptBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                decryptBytes[i] = (byte) (255 - bytes[i]);
            }

            // 关闭
            fileInputStream.close();

            // 将解密的字节流数组生成指定类名的字节码文件（即 Hello.class）
            return defineClass(name, decryptBytes, 0, decryptBytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
