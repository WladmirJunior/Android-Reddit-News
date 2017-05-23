package wlad.com.netbeetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wlad on 22/05/17.
 */

public class NewsList {

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("data")
    public NewsListData newsListData;

    public NewsList() {
    }

    public NewsList(String kind, NewsListData newsListData) {
        this.kind = kind;
        this.newsListData = newsListData;
    }
}
