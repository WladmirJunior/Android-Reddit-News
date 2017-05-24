package wlad.com.netbeetest.models;

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

    List<News> getNewsList() {
        return newsList;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
