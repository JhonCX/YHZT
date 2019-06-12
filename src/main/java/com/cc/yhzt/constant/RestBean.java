package com.cc.yhzt.constant;

import lombok.Data;

/**
 * @author cc
 */
@Data
public class RestBean {
    private int code;

    private String msg;

    private Long count;

    private Object data;



    public RestBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestBean(int code, String msg,Long total, Object data) {
        this.code = code;
        this.msg = msg;
        this.count=total;
        this.data = data;
    }
}
