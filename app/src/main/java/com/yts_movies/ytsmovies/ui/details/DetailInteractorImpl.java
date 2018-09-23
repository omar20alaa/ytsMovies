package com.yts_movies.ytsmovies.ui.details;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.yts_movies.ytsmovies.ui.base.BaseInteractorImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DetailInteractorImpl extends BaseInteractorImpl implements DetailInteractor {


  @Override
  public void loadMovieDetails(Activity context, long movieId, DetailListener listener) {
    api.getMovieDetails(movieId, true, true)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(listener::onDetailSubscribe)
        .doOnComplete(listener::onDetailComplete)
        .subscribe(detail -> listener.subscribeDetail(context, detail), throwable -> {
          listener.onDetailError();
          Timber.e(throwable);
        });
  }

  @Override
  public void downloadFile(String url, DetailListener listener) {
    api.downloadFile(url)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(listener::writeFileToDisk)
        .doOnSubscribe(listener::onDownloadSubscribe)
        .doOnComplete(listener::onDownloadComplete)
        .subscribe(listener::subscribeDownload, listener::onDownloadError);
  }
}
