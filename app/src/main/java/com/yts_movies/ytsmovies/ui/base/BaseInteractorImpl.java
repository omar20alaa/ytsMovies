package com.yts_movies.ytsmovies.ui.base;

import com.yts_movies.ytsmovies.data.network.connection.RetrofitConnection;
import com.yts_movies.ytsmovies.data.network.webservice.BaseApi;

public class BaseInteractorImpl {

  protected BaseApi api = new RetrofitConnection().initRetrofit();
}
