package com.android.core.sample.bean;


/**
 * 必须和接口返回的数据格式一致
 * <pre>
 *     {
 "date": "20161229",
 "stories": [{
 "images": ["http:\/\/pic2.zhimg.com\/ede5105be8cbcc2fed4f63a952354fe5.jpg"],
 "type": 0,
 "id": 9106192,
 "ga_prefix": "122912",
 "title": "大误 · 你们音乐家真厉害"
 }, {
 "images": ["http:\/\/pic1.zhimg.com\/cb5cdeddfe77d063356241cecceeeb50.jpg"],
 "type": 0,
 "id": 9106246,
 "ga_prefix": "122911",
 "title": "工信部说手机预装软件必须可卸载，能有用吗？"
 }
 }
 * </pre>
 * Created by tengfei.lv on 2016/12/29.
 */

public class JsonResult<T> {
    private String date;
    private T top_stories;

    private boolean error;

    private T results;

    public boolean isError () {
        return error;
    }

    public void setError (boolean error) {
        this.error = error;
    }

    public T getResults () {
        return results;
    }

    public void setResults (T results) {
        this.results = results;
    }

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public T getTop_stories () {
        return top_stories;
    }

    public void setTop_stories (T top_stories) {
        this.top_stories = top_stories;
    }
}
