package com.android.core.sample.bean;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by tengfei.lv on 2017/1/4.
 */

public class MeiziBean implements Serializable {
    private String desc;
    private String publishedAt;
    private String url;
    private String who;

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getDesc () {
        return desc;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public String getPublishedAt () {
        return publishedAt;
    }

    public void setPublishedAt (String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getWho () {
        return who;
    }

    public void setWho (String who) {
        this.who = who;
    }

     public int getItemType () {
        return new Random ().nextInt (10) % 2;
    }
}
