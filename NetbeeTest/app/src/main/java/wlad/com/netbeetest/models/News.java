package wlad.com.netbeetest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wlad on 22/05/17.
 */

public class News {

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("data")
    private NewsData newsData;

    public NewsData getNewsData() {
        return newsData;
    }
}
