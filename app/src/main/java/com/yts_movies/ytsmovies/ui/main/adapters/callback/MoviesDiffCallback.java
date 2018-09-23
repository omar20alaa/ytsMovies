package com.yts_movies.ytsmovies.ui.main.adapters.callback;

import android.support.v7.util.DiffUtil;

import com.yts_movies.ytsmovies.data.network.model.BaseMovie;

import java.util.List;

public class MoviesDiffCallback extends DiffUtil.Callback {

    // vars
    private List<BaseMovie.Movie> oldMovieList;
    private List<BaseMovie.Movie> newMovieList;

    public MoviesDiffCallback(List<BaseMovie.Movie> oldMovieList, List<BaseMovie.Movie> newMovieList) {
        this.oldMovieList = oldMovieList;
        this.newMovieList = newMovieList;
    } // constructor

    @Override
    public int getOldListSize() {
        return oldMovieList != null ? oldMovieList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newMovieList != null ? newMovieList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).getId() == newMovieList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).equals(newMovieList.get(newItemPosition));
    }
}
