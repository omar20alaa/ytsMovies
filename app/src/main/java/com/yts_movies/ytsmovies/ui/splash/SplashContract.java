package com.yts_movies.ytsmovies.ui.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yts_movies.ytsmovies.R;
import com.yts_movies.ytsmovies.ui.base.BaseInteractor;
import com.yts_movies.ytsmovies.ui.base.BasePresenter;
import com.yts_movies.ytsmovies.ui.base.BaseView;

public interface SplashContract {

    interface View extends BaseView<Presenter>{

        void openMainActivity();

        @Override
        void setActionBar();

        @Override
        void showProgressBar();

        @Override
        void hideProgressBar();
    }

    interface Presenter extends BasePresenter<View , BaseInteractor>{}


}
