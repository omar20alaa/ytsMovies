package com.yts_movies.ytsmovies.ui.details.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yts_movies.ytsmovies.R;
import com.yts_movies.ytsmovies.data.network.model.MovieDetail;
import com.yts_movies.ytsmovies.utils.GlideUtil;
import com.yts_movies.ytsmovies.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter  extends RecyclerView.Adapter<CastAdapter.CastHolder>{

    private Context context;
    private List<MovieDetail.Cast> casts;

    public CastAdapter(Context context, List<MovieDetail.Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @Override public CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowRoot = inflater.inflate(R.layout.item_cast, parent, false);
        return new CastHolder(rowRoot);
    }

    @Override public void onBindViewHolder(CastHolder holder, int position) {
        MovieDetail.Cast cast = casts.get(position);
        if (cast.getUrlSmallImage() != null && !Utils.isEmpty(cast.getUrlSmallImage())) {
            GlideUtil.loadImg(context, cast.getUrlSmallImage(), holder.castImageView);
        }
        if (cast.getName() != null && !Utils.isEmpty(cast.getName())) {
            holder.castTextView.setText(cast.getName());
        }
    }

    @Override public int getItemCount() {
        return casts.size();
    }

    class CastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_cast_iv_pic)
        CircleImageView castImageView;
        @BindView(R.id.item_cast_tv_name)
        TextView castTextView;

        CastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
