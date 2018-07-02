package com.example.demo.domain;

import java.io.Serializable;

public class XAxis implements Serializable {
    private static final long serialVersionUID = 456445838819619965L;
    private String type;
    private String[] data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
