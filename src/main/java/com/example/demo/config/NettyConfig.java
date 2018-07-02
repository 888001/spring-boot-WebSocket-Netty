package com.example.demo.config;


import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

public class NettyConfig {
    //存储每一个客户端接入进来的通道组,set集合
    //按每个网页分组
    Map<String, ChannelGroup> groupMap = new HashMap<>();
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
