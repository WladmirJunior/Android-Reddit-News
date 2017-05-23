package wlad.com.netbeetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wlad on 22/05/17.
 */

public class NewsListData {

    @JsonProperty("modhash")
    private String modhash;

    @JsonProperty("children")
    private List<News> newsList;

    @JsonProperty("after")
    private String after;

    @JsonProperty("before")
    private Object before;

    public List<News> getNewsList() {
        return newsList;
    }

    public NewsListData() {
    }

    public NewsListData(String modhash, List<News> newsList, String after, Object before) {
        this.modhash = modhash;
        this.newsList = newsList;
        this.after = after;
        this.before = before;
    }
}
