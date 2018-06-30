package com.example.demo.controller;

import com.example.demo.config.NettyConfig;
import com.example.demo.domain.Result;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：hpp
 * @date：2018/6/29 13:35
 * @description:
 */
@RestController
public class RestController1 {
    @RequestMapping("/send")
    public void send() {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame( "6666677777");
        //服务端向每个连接上来的客户端发送消息
        //NettyConfig.group.writeAndFlush(textWebSocketFrame);
        Result result = new Result();
        result.setCode(666);

        NettyConfig.group.writeAndFlush(result.toJSONString());
    }
}
