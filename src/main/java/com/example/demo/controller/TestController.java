package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.BiddingUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：hpp
 * @date：2018/7/2 9:25
 * @description:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    KafkaTemplate kafkaTemplate;
    @RequestMapping("/kafka")
    @ResponseBody
    public void testkafka() throws Exception {
        BiddingUserBean bean = new BiddingUserBean();
        bean.setId("111");
        bean.setCproductname("腾讯");
        bean.setIphone("18888888888");
        kafkaTemplate.send("boot", JSONObject.toJSONString(bean));
    }
}
