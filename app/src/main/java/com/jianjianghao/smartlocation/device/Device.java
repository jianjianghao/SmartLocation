package com.jianjianghao.smartlocation.device;

/**
 * Created by ag on 5/1/2015.
 */
public class Device {
    private int mId;
    private String mName;
    private String mContent;
    private String mMan;
    private String mNumber;
    private int mState;

    public Device(int id, String name, String content,  String man, String number,int state) {
        mId = id;
        mState = state;
        mName = name;
        mContent = content;
        mMan = man;
        mNumber = number;
    }
    public int getId() {
        return mId;
    }
    public void setId(int id) {
        mId = id;
    }
    public int getState() {
        return mState;
    }
    public void setState(int state) {
        mState = state;
    }
    public String getContent() {
        return mContent;
    }
    public void setContent(String content) {
        mContent = content;
    }

    public String getMan() {
        return mMan;
    }
    public void setMan(String man) {
        mMan = man;
    }

    public String getNumber() {
        return mNumber;
    }
    public void setNumber(String number) {
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
