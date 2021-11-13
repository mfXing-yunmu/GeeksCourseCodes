package com.yunmu.geek.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunmu
 */
@RestController
public class OkHttpController {

    /**
     * 用于测试 OkHttp 请求
     *
     * @param name 入参
     * @return String
     */
    @PostMapping("/geek/okhttp")
    public String getOkHttpRes(@RequestBody String name){
        return "Hello " + name + ", This is Geek OkHttp request!";
    }
}
