package com.zengqiang.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    //返回的消息
    private String message;
    /*100表示成功，200表示失败*/
    private int code;

    /*obj存放返回给jsp页面的json对象,String表示索引*/
    private Map<String,Object> obj =new HashMap<String,Object>();

    //成功信息
    public static Msg success(){
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMessage("处理成功");
        return msg;
    }

    //失败信息
    public static Msg fail(){
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMessage("处理失败");
        return msg;
    }

    //该方法不是静态static方法的
    public Msg add(String key,Object jsonObj){
        this.obj.put(key,jsonObj);
        return this;
    }



    public Msg() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getObj() {
        return obj;
    }

    public void setObj(Map<String, Object> obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", obj=" + obj +
                '}';
    }
}
