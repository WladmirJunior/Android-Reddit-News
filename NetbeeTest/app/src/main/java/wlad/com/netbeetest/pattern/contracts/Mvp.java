package wlad.com.netbeetest.pattern.contracts;

import android.content.Context;

import java.util.List;

/**
 * Created by wlad on 24/05/17.
 */

public interface Mvp {

    interface ViewOperations<T> {
        Context getViewContext();
        void showToast(String msg);
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void hideRefresh();
        void updateList(List list);
        void addList(T element);
        void saveViewElements(T element);
    }

    interface PresenterOperations<T>{
        void onConfigurationChanged(ViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void updateRetainItems(List list, T element);
        void getItems();
        void updateItems();
        void getMoreItems();
        void openItem(Object item);
    }

    interface RequiredModelPresenterOperations<T> {
        void onReceiverResponse(T element);
        void onError(String error);
    }

    interface ModelOperations{
        void getResponse();
        void getResponse(String path);
        void onDestroy();
    }
}
