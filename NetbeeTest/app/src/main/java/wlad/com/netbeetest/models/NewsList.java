package wlad.com.netbeetest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wlad on 22/05/17.
 */

public class NewsList {

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("data")
    private NewsListData newsListData;

    public List<News> getNewsList() {
        return newsListData.getNewsList();
    }
}
