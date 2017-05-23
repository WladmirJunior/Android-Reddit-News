package wlad.com.netbeetest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wlad on 22/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsData {

    @JsonProperty("author")
    public String author;

    @JsonProperty("domain")
    public String domain;

    @JsonProperty("thumbnail")
    public String thumbnail;

    @JsonProperty("url")
    public String url;

    @JsonProperty("title")
    public String title;

    @JsonProperty("num_comments")
    public Integer numComments;

    public NewsData() {
    }

    public NewsData(String author, String domain, String thumbnail, String url, String title, Integer numComments) {
        this.author = author;
        this.domain = domain;
        this.thumbnail = thumbnail;
        this.url = url;
        this.title = title;
        this.numComments = numComments;
    }
}
