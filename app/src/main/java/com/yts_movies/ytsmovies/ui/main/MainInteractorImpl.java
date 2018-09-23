package com.yts_movies.ytsmovies.ui.main;

import android.content.Context;

import com.yts_movies.ytsmovies.ui.base.BaseInteractor;
import com.yts_movies.ytsmovies.ui.base.BaseInteractorImpl;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainInteractorImpl extends BaseInteractorImpl implements MainInteractor {

    @Override
    public void loadMoviesList(Context context, int pageNum, boolean refresh, MainListener listener) {
        api.getMoviesList(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> listener.onSubscribe(disposable, pageNum, refresh))
                .doOnComplete(() -> listener.onComplete(pageNum, refresh))
                .subscribe(moviesModel -> listener.subscribe(moviesModel, refresh),
                        throwable -> listener.onError(throwable, pageNum, refresh)
                );
    }
}
