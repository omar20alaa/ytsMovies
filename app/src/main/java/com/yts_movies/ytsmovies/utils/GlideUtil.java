package com.yts_movies.ytsmovies.utils;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yts_movies.ytsmovies.ui.main.adapters.MoviesAdapter;


@SuppressWarnings("unchecked")
public class GlideUtil {

    private enum Type {
        ITEM, YOUTUBE, POSTER
    }

    public static void loadImg(Context context, String url, ImageView target) {
        Glide.with(context).load(Uri.parse(url)).into(target);
    }

    public static void loadItemImg(Context context, MoviesAdapter.MoviesHolder holder, String url) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.moviePosterImageView);
    }

    public static void loadYouTubeThumb(Context context, CollapsingToolbarLayout toolbar,
                                        ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

    public static void loadPoster(Context context, CardView card, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }


}
