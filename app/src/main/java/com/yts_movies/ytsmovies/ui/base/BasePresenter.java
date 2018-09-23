package com.yts_movies.ytsmovies.ui.base;

public interface BasePresenter<V extends  BaseView, I extends BaseInteractor> {

    void onAttatch(V View , I interactor);

    void onDestroy();
}
