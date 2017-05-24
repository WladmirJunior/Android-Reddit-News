package wlad.com.netbeetest.pattern;

import android.content.Context;

import java.util.List;

/**
 * Created by wlad on 24/05/17.
 */

public interface Mvp {

    /**
     * Métodos obrigatórios em View, disponíveis para Presenter
     *      Presenter -> View
     */
    interface RequiredViewOperations<T>{
        Context getContext();
        void showToast(String msg);
        void showAlert(String msg);
        void showLoad();
        void hideLoad();
        void hideRefresh();
        void updateList(List<T> list);
    }

    /**
     * operações oferecidas ao layer View para comunicação com Presenter
     *      View -> Presenter
     */
    interface PresenterOperations{
        void onConfigurationChanged(RequiredViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void getItems();
        void updateItems();
        void getMoreItems();
        void openItem(Object item);
    }

    /**
     * operações oferecidas pelo layer Presenter para comunicações com NewsListModel
     *      NewsListModel -> Presenter
     */
    interface RequiredPresenterOperations<T>{
        void onReceiverResponse(List<T> list);
        void onError(String error);
    }

    /**
     * operações oferecidos pelo layer NewsListModel para comunicações com Presenter
     *      Presenter -> NewsListModel
     */
    interface ModelOperations{
        void getResponse();
        void onDestroy();
    }
}
