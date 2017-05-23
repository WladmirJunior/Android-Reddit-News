package wlad.com.netbeetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wlad on 22/05/17.
 */

public class News {

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("data")
    public NewsData newsData;

    public News() {
    }

    public News(String kind, NewsData newsData) {
        this.kind = kind;
        this.newsData = newsData;
    }
}
