package wlad.com.netbeetest.pattern.contracts;

import java.util.List;

import wlad.com.netbeetest.models.NewsData;

/**
 * Created by wlad on 24/05/17.
 */

public interface Mvp {

    /* Views */
    interface MainViewOperations {
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void hideRefresh();
        void showListLoad();
        void hideListLoad();
        void updateList(Object element);
        void addList(Object element);
        void saveViewElements(Object element);
        void goToScreen(Class screen, NewsData data);
    }

    interface NewsViewOperations<T> {
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void updateList(List list);
        void saveViewElements();
        void openUrl(String url);
    }

    /* Presenters */
    interface MainPresenterOperations<T>{
        void onConfigurationChanged(MainViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void reloadSavedElements(Object element);
        void openRecycle();
        void swipeToRefresh();
        void endingRecycle();
        void clickOnItemRecycle(Object item);
    }

    interface NewsPresenterOperations<T>{
        void onConfigurationChanged(NewsViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void reloadSavedElements(List list, T element);
        void openRecycle();
        void clickItem(Object item);
    }

    interface NewsModelOperations {
        void getNews();
        void getNews(String query);
        void getComments(String path);
        void onDestroy();
    }
}
