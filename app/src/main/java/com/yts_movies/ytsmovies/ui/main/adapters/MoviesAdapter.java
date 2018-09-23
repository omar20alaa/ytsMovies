package com.yts_movies.ytsmovies.ui.main.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yts_movies.ytsmovies.R;
import com.yts_movies.ytsmovies.data.bus.MovieData;
import com.yts_movies.ytsmovies.data.network.model.BaseMovie;
import com.yts_movies.ytsmovies.ui.details.DetailsActivity;
import com.yts_movies.ytsmovies.ui.main.adapters.callback.MoviesDiffCallback;
import com.yts_movies.ytsmovies.utils.GlideUtil;
import com.yts_movies.ytsmovies.utils.ToastUtil;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    // vars
    public static final String KEY_MOVIE_DATA = "movieData";
    private Activity context;
    private List<BaseMovie.Movie> movieList;


    public MoviesAdapter(Activity context, List<BaseMovie.Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    } // constructor

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowRoot = inflater.inflate(R.layout.item_movies_list, parent, false);
        return new MoviesHolder(rowRoot);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        fillViewWithData(holder, movieList.get(position));
    }

    private void fillViewWithData(MoviesHolder holder, BaseMovie.Movie movie) {
        GlideUtil.loadItemImg(context, holder, movie.getPoster());
        holder.movieNameTextView.setText(movie.getTitle());
        holder.movieYearTextView.setText(movie.getYear());
        List<String> genres = movie.getGenres();
        if (genres != null && genres.size() > 0) {
            holder.movieGenreTextView.setText(movie.getGenres().get(0));
            holder.movieGenreTextView.setVisibility(View.VISIBLE);
        } else {
            holder.movieGenreTextView.setVisibility(View.GONE);
        }
    }

    public void updateData(List<BaseMovie.Movie> movieList, boolean newList) {
        if (newList) {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                    new MoviesDiffCallback(this.movieList, movieList)
            );
            this.movieList.clear();
            result.dispatchUpdatesTo(this);
        }
        this.movieList.addAll(movieList);
        if (!newList) notifyItemRangeInserted(getItemCount(), this.movieList.size() - 1);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MoviesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_card) public CardView rowCardView;
        @BindView(R.id.item_iv_poster) public ImageView moviePosterImageView;
        @BindView(R.id.item_relative_tintable) public RelativeLayout tintAbleView;
        @BindView(R.id.item_tv_movie_name) public TextView movieNameTextView;
        @BindView(R.id.item_tv_movie_year) public TextView movieYearTextView;
        @BindView(R.id.item_tv_movie_genre) public TextView movieGenreTextView;
        @BindView(R.id.item_view_shared) View sharedView;

        @BindString(R.string.no_network) String noConnection;


        public MoviesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
        @OnClick
        void onClick(View view) {
            if (com.yts_movies.ytsmovies.utils.Utils.isNetworkAvailable(view.getContext())) {
                BaseMovie.Movie movie = movieList.get(getLayoutPosition());
                MovieData data = new MovieData(movie.getId(), movie.getTitle(), movie.getYoutubeCode());
                startAnimatedActivity(context, DetailsActivity.class, data, sharedView);
            } else {
                ToastUtil.showShortToast(view.getContext(), noConnection);
            }
        }
    }
    private void startAnimatedActivity(Activity context, Class cls, MovieData data, View shared) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, shared, "");
        Intent intent = new Intent(context, cls);
        intent.putExtra(KEY_MOVIE_DATA, data);
        context.startActivity(intent, options.toBundle());
    }
}
