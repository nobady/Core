package com.android.core.sample.bean;

import java.io.Serializable;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class TestBean implements Serializable {
    private String image;
    private String title;

    public String getImage () {
        return image;
    }

    public void setImage (String images) {
        this.image = images;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }
}
