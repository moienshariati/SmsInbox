package com.example.smsinbox;

public class SMSData {
    private String number;
    private String body;
    private int id;

    public SMSData(String number, String body, int id) {
        this.number = number;
        this.body = body;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
