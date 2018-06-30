package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：hpp
 * @date：2018/6/29 15:43
 * @description:
 */
public class Dopost {
    public static void main(String[] a) {
        try {
            Map<String, String> aa = new HashMap<>();
            aa.put("sss", null);
            URLEncoder.encode(String.valueOf(aa.get("sss")),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
