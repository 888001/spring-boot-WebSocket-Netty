package com.example.demo.domain;

import java.io.Serializable;

public class YAxis implements Serializable {
    private static final long serialVersionUID = 8306716425588637810L;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
