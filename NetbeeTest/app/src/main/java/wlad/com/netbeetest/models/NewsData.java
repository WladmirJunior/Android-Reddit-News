package wlad.com.netbeetest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by wlad on 22/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsData implements Serializable{

    @JsonProperty("author")
    private String author;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    @JsonProperty("num_comments")
    private Integer numComments;

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("body")
    private String body;

    public String getAuthor() {
        return author;
    }

    public String getDomain() {
        return domain;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getBody() {
        return body;
    }
}
