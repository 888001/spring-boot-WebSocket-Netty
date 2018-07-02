package com.example.demo.domain;

import java.io.Serializable;

public class Series implements Serializable {
    private static final long serialVersionUID = 8662462967754368295L;
    private String[] data;
    private String type;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
