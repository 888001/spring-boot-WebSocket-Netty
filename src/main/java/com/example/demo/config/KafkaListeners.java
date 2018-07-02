package com.example.demo.config;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.BiddingUserBean;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author：hpp
 * @date：2018/7/2 9:22
 * @description:
 */
public class KafkaListeners {

    @KafkaListener(topics = {"boot"})
    public void processMessage(String content) {
        //BiddingUserBean m = (BiddingUserBean) JSONObject.parse(content);
        System.out.println(content);
        System.out.println(Thread.currentThread().getName());
    }
}
