package com.example.hrh.testweatherinfo.data.TestRobotBean;

/**
 * Created by hrh on 2015/11/26.
 */
public class TestRobotResult {

    @Override
    public String toString() {
        return "TestRobotResult{" +
                "code=" + code +
                ", text='" + text + '\'' +
                '}';
    }

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
