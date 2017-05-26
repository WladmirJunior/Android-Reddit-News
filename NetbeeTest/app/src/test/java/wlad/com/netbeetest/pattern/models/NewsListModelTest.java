package wlad.com.netbeetest.pattern.models;

import org.junit.Test;

/**
 * Created by wlad on 24/05/17.
 */
public class NewsListModelTest {

    @Test
    public void test1(){
        NewsListModel model = new NewsListModel();
        model.getNews();
    }

    @Test
    public void test2(){
        NewsListModel model = new NewsListModel();
        model.getNews("path");
    }

    @Test
    public void test3(){
        NewsListModel model = new NewsListModel();
        model.getNews("https://www.reddit.com/r/Android/new/");
    }

    @Test
    public void test4(){
        NewsListModel model = new NewsListModel();
        model.getNews("t3_6d3w3l");
    }

}