package com.yts_movies.ytsmovies.ui.details;

import android.app.Activity;

import com.yts_movies.ytsmovies.data.network.model.MovieDetail;
import com.yts_movies.ytsmovies.ui.base.BaseInteractor;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface DetailInteractor extends BaseInteractor {

  interface DetailListener {

    void onDetailSubscribe(Disposable disposable);

    void onDownloadSubscribe(Disposable disposable);

    void onDetailComplete();

    void onDownloadComplete();

    void subscribeDetail(Activity context, MovieDetail model);

    void subscribeDownload(boolean isSaved);

    void onDetailError();

    void onDownloadError(Throwable throwable);

    Observable<Boolean> writeFileToDisk(Response<ResponseBody> response);
  }

  void loadMovieDetails(Activity context, long movieId, DetailListener listener);

  void downloadFile(String url, DetailListener listener);
}
