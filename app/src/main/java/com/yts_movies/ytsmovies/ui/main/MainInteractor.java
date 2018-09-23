package com.yts_movies.ytsmovies.ui.main;

import android.content.Context;

import com.yts_movies.ytsmovies.data.network.model.BaseMovie;
import com.yts_movies.ytsmovies.ui.base.BaseInteractor;

import io.reactivex.disposables.Disposable;

public interface MainInteractor extends BaseInteractor {

    interface MainListener {

        void onSubscribe(Disposable disposable , int pageNum, boolean refresh);

        void onComplete(int pageNum, boolean refresh);

        void subscribe(BaseMovie model, boolean refresh);

        void onError(Throwable throwable, int pageNum, boolean refresh);
    }

    void loadMoviesList(Context context, int pageNum, boolean refresh, MainListener listener);
}
