package com.yts_movies.ytsmovies.ui.splash;

import com.yts_movies.ytsmovies.ui.base.BaseInteractor;

public class SplashPresenter implements SplashContract.Presenter {

    // vars
    private SplashContract.View view;

    @Override
    public void onAttatch(SplashContract.View view, BaseInteractor interactor) {
        this.view = view;
        this.view.setPresenter(this);
        this.view.openMainActivity();
    } // on attatch function

    @Override
    public void onDestroy() {
        view = null;
    } // on destroy function
}
