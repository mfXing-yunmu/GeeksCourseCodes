package com.yunmu.geek;

import okhttp3.*;
import java.io.IOException;

/**
 * @author yunmu
 */
public class OkHttpDemo {
    /** 资源解析类型 */
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /** 初始化 HTTP 客户端 */
    public static OkHttpClient okHttpClient = new OkHttpClient();

    /** POST 请求 */
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws Exception {
        /** 声明请求 URL（自己启动的一个Tomcat，用于测试 OkHttp 请求） */
        String url = "http://localhost:8801/geek/okhttp";
        String name = "yunmu";

        /** 发送 POST 请求 */
        String response = OkHttpDemo.post(url, name);
        System.out.println("Request name: " + name + ", url: " + url);
        System.out.println("Response: " + response);

        /** 回收对象 */
        OkHttpDemo.okHttpClient = null;

        /**
         * 运行结果如下：
         *
         * Request name: yunmu, url: http://localhost:8801/geek/okhttp
         * Response: Hello yunmu, This is Geek OkHttp request!
         */
    }
}
