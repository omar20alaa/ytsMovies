package com.yts_movies.ytsmovies.ui.base;

public interface BaseView<T extends  BasePresenter> {

    void setPresenter(T presenter);

    void setActionBar();

    void showProgressBar();

    void hideProgressBar();
}
