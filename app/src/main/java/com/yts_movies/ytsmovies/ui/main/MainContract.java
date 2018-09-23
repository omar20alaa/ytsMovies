package com.yts_movies.ytsmovies.ui.main;

import android.content.Context;

import com.yts_movies.ytsmovies.data.network.model.BaseMovie;
import com.yts_movies.ytsmovies.ui.base.BasePresenter;
import com.yts_movies.ytsmovies.ui.base.BaseView;
import com.yts_movies.ytsmovies.ui.splash.SplashContract;

import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter>{

        void setupRecycler();

        void setupRefreshLayout();

        void loadList();

        void setRecyclerAdapter(List<BaseMovie.Movie> moviesList, boolean refresh);

        void enableRefreshLayout();

        void showRefreshLayout();

        void hideRefreshLayout();

        void showLoadingBar();

        void hideLoadingBar();

        void showRecycler();

        void hideRecycler();

        void showNoConnectionView();

        void showEmptyLisView();

        void showErrorView();

        void hideHolderViews();

    }


    interface Presenter extends BasePresenter<View , MainInteractor>{
        void loadMoviesList(Context context, int pageNum, boolean refresh);
    }
}
