package com.jianjianghao.smartlocation.phone;

/**
 * Created by ag on 5/1/2015.
 */
public class Phone {
    private int mId;
    private String mName;
    private String mContent;
    private int mImportant;

    public Phone(int id, String name,  String content, int important) {
        mId = id;
        mImportant = important;
        mName = name;
        mContent = content;
    }
    public int getId() {
        return mId;
    }
    public void setId(int id) {
        mId = id;
    }
    public int getImportant() {
        return mImportant;
    }
    public void setImportant(int important) {
        mImportant = important;
    }
    public String getContent() {
        return mContent;
    }
    public void setContent(String content) {
        mContent = content;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
