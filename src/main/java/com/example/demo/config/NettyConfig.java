package com.example.demo.config;


import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NettyConfig {
    //存储每一个客户端接入进来的通道组,set集合
    //按每个网页进行分组
    public static Map<String, ChannelGroup> groupMap = new HashMap<>();

    static {

        ChannelGroup group1 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group2 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group3 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group4 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group5 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group6 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group7 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group8 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        ChannelGroup group9 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        groupMap.put("group1", group1);
        groupMap.put("group2", group2);
        groupMap.put("group3", group3);
        groupMap.put("group4", group4);
        groupMap.put("group5", group5);
        groupMap.put("group6", group6);
        groupMap.put("group7", group7);
        groupMap.put("group8", group8);
        groupMap.put("group9", group9);
    }

    public static void remove(Object o) {
        for (Map.Entry kv : groupMap.entrySet()) {
            ChannelGroup c = (ChannelGroup) kv.getValue();
            boolean isBreak = false;
            if (c.remove(o)) {
                isBreak = true;
            }
            if (isBreak) {
                break;
            }
        }
    }
}
