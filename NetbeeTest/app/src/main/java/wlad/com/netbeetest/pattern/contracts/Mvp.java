package wlad.com.netbeetest.pattern.contracts;

import java.util.List;

/**
 * Created by wlad on 24/05/17.
 */

public interface Mvp {

    interface ViewOperations<T> {
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void hideRefresh();
        void updateList(List list);
        void addList(T element);
        void saveViewElements(T element);
        void openUrl(String url);
    }

    interface PresenterOperations<T>{
        void onConfigurationChanged(ViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void reloadSavedElements(List list, T element);
        void openRecycle();
        void swipeToRefresh();
        void endingRecycle();
        void clickOnItemRecycle(Object item);
    }

    interface ModelOperations{
        void getResponse();
        void getResponse(String path);
        void onDestroy();
    }
}
