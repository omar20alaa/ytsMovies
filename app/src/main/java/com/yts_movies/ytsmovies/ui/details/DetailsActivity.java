package com.yts_movies.ytsmovies.ui.details;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yts_movies.ytsmovies.R;
import com.yts_movies.ytsmovies.data.Quality;
import com.yts_movies.ytsmovies.data.bus.MovieData;
import com.yts_movies.ytsmovies.data.network.Urls;
import com.yts_movies.ytsmovies.ui.base.BaseActivity;
import com.yts_movies.ytsmovies.ui.details.adapter.pager.ScreenshotsAdapter;
import com.yts_movies.ytsmovies.ui.details.adapter.recycler.CastAdapter;
import com.yts_movies.ytsmovies.ui.main.adapters.MoviesAdapter;
import com.yts_movies.ytsmovies.utils.DialogUtil;
import com.yts_movies.ytsmovies.utils.GlideUtil;
import com.yts_movies.ytsmovies.utils.ToastUtil;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class DetailsActivity extends BaseActivity implements DetailContract.View{

    // bind views
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_iv_toolbar_trailer_thumb)
    ImageView trailerThumbImageView;
    @BindView(R.id.detail_iv_background) ImageView backgroundImageView;
    @BindView(R.id.detail_constraint)
    ConstraintLayout detailConstraint;
    @BindView(R.id.detail_progress_bar)
    ProgressWheel detailProgressBar;
    @BindView(R.id.detail_card_view)
    CardView posterCardView;
    @BindView(R.id.detail_iv_poster) ImageView posterImageView;
    @BindView(R.id.detail_tv_res_3d)
    TextView res3DTexView;
    @BindView(R.id.detail_tv_res_720) TextView res720TexView;
    @BindView(R.id.detail_tv_res_1080) TextView res1080TexView;
    @BindView(R.id.detail_tv_year) TextView yearTextView;
    @BindView(R.id.detail_tv_genre) TextView genreTextView;
    @BindView(R.id.detail_tv_rate) TextView rateTextView;
    @BindView(R.id.detail_tv_synopsis) TextView synopsisTextView;
    @BindView(R.id.detail_tv_overview) TextView overviewTextView;
    @BindView(R.id.detail_divider1)
    View screenshotDivider;
    @BindView(R.id.detail_tv_screenshots) TextView screenshotsTextView;
    @BindView(R.id.detail_pager_screenshots)
    ViewPager screenshotsPager;
    @BindView(R.id.detail_divider2) View castDivider;
    @BindView(R.id.detail_tv_cast) TextView castTextView;
    @BindView(R.id.detail_recycler_cast)
    RecyclerView castRecyclerView;

    @BindString(R.string.dialog_download_torrent) String dialogTitle;
    @BindString(R.string.dialog_download) String dialogDownload;
    @BindString(R.string.dialog_copy_magnet) String dialogCopyMagnet;
    @BindString(R.string.dialog_dismiss) String dialogDismiss;
    @BindString(R.string.msg_magnet_copied) String copied;
    @BindString(R.string.dialog_title_downloading) String dialogDownloading;
    @BindString(R.string.dialog_wait) String dialogWait;
    @BindString(R.string.msg_download_success) String saveSuccess;
    @BindString(R.string.msg_download_failed) String saveFailed;
    @BindString(R.string.no_network) String noConnection;
    @BindString(R.string.perm_not_granted) String permNotGranted;

    @BindDrawable(R.drawable.ic_3d_quality)
    Drawable quality3Drawable;
    @BindDrawable(R.drawable.ic_720p_quality) Drawable quality720pDrawable;
    @BindDrawable(R.drawable.ic_1080p_quality) Drawable quality1080pDrawable;

    // vars
    private DetailContract.Presenter presenter;
    private DialogUtil dialogUtil;
    private MaterialDialog dialog;
    private MovieData data;
    private String youtubeCode;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override protected void initPresenter() {
        new DetailPresenter().onAttatch(this, new DetailInteractorImpl());
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override public void receiveData() {
        Intent data = getIntent();
        if (data != null && data.getExtras() != null) {
            this.data = data.getParcelableExtra(MoviesAdapter.KEY_MOVIE_DATA);
            if (this.data != null) {
                youtubeCode = this.data.getYoutubeCode();
                setTitle(this.data.getMovieName());
            }
        }
    }

    @Override public void setupRecycler() {
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
    }

    @Override public void initDialog() {
        dialogUtil = new DialogUtil(getContext());
    }

    @Override public void loadDetails() {
        if (data != null) presenter.loadMovieDetails(getContext(), data.getMovieId());
    }

    @Override public void loadImages(String tubeUrl, String posterUrl, String backgroundUrl) {
        GlideUtil.loadYouTubeThumb(getContext(), collapsingToolbar, trailerThumbImageView, tubeUrl);
        GlideUtil.loadPoster(getContext(), posterCardView, posterImageView, posterUrl);
        GlideUtil.loadImg(getContext(), backgroundUrl, backgroundImageView);
    }

    @Override public void setInfo(String year, String genre, String rate, String description) {
        yearTextView.setText(year);
        genreTextView.setText(genre);
        rateTextView.setText(getString(R.string.detail_vote, rate));
        overviewTextView.setText(description);
    }

    @Override
    public void setPagerAdapter(ScreenshotsAdapter adapter) {
        screenshotsPager.setAdapter(adapter);
    }

    @Override
    public void setCastRecyclerAdapter(CastAdapter adapter) {
        castRecyclerView.setAdapter(adapter);
    }


    @Override public void showProgressBar() {
        detailProgressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgressBar() {
        detailProgressBar.setVisibility(View.GONE);
    }

    @Override public void showMainView() {
        detailConstraint.setVisibility(View.VISIBLE);
    }

    @Override public void showYear() {
        yearTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideYear() {
        yearTextView.setVisibility(View.GONE);
    }

    @Override public void showGenres() {
        genreTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideGenres() {
        genreTextView.setVisibility(View.GONE);
    }

    @Override public void showRate() {
        rateTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideRate() {
        rateTextView.setVisibility(View.GONE);
    }

    @Override public void enable3D() {
        res3DTexView.setEnabled(true);
        res3DTexView.setTextColor(Color.WHITE);
    }

    @Override public void enable720p() {
        res720TexView.setEnabled(true);
        res720TexView.setTextColor(Color.WHITE);
    }

    @Override public void enable1080p() {
        res1080TexView.setEnabled(true);
        res1080TexView.setTextColor(Color.WHITE);
    }

    @Override public void hideSynopsis() {
        screenshotDivider.setVisibility(View.GONE);
        overviewTextView.setVisibility(View.GONE);
        synopsisTextView.setVisibility(View.GONE);
    }

    @Override public void hideScreenshots() {
        screenshotDivider.setVisibility(View.GONE);
        screenshotsTextView.setVisibility(View.GONE);
        screenshotsPager.setVisibility(View.GONE);
    }

    @Override public void hideCast() {
        castDivider.setVisibility(View.GONE);
        castTextView.setVisibility(View.GONE);
        castRecyclerView.setVisibility(View.GONE);
    }

    @Override public void showProgressDialog() {
        MaterialDialog.Builder builder = dialogUtil.buildProgressDialog(dialogDownloading, dialogWait);
        builder.cancelable(false);
        dialog = builder.build();
        dialog.show();
    }

    @Override public void dismissProgressDialog() {
        if (dialog != null) dialog.dismiss();
    }

    @Override public void showFileSavedToast() {
        ToastUtil.showLongToast(getContext(), saveSuccess);
    }

    @Override public void showFileNotSavedToast() {
        ToastUtil.showLongToast(getContext(), saveFailed);
    }

    @Override public void showCopyConfirmToast() {
        ToastUtil.showShortToast(getContext(), copied);
    }

    @Override public void showNoConnectionToast() {
        ToastUtil.showShortToast(getContext(), noConnection);
    }

    @Override public void showPermissionNotGrantedToast() {
        ToastUtil.showLongToast(getContext(), permNotGranted);
    }

    @OnClick({
            R.id.detail_frame_thumb, R.id.detail_tv_res_3d,
            R.id.detail_tv_res_720, R.id.detail_tv_res_1080
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_frame_thumb:
                Uri youTubeUri = Uri.parse(Urls.getYouTubeUrl(youtubeCode));
                startActivity(new Intent(Intent.ACTION_VIEW, youTubeUri));
                break;
            case R.id.detail_tv_res_3d:
                showTorrentInfoDialog(Quality.Q_3D);
                break;
            case R.id.detail_tv_res_720:
                showTorrentInfoDialog(Quality.Q_720P);
                break;
            case R.id.detail_tv_res_1080:
                showTorrentInfoDialog(Quality.Q_1080P);
                break;
        }
    }

    private void showTorrentInfoDialog(Quality quality) {
        MaterialDialog.Builder builder = dialogUtil.buildDialog(R.layout.dialog_download);
        builder.title(dialogTitle);
        setupInfoDialogButtons(builder, quality);
        MaterialDialog matDialog = builder.build();
        matDialog.setCancelable(true);
        View dialogView = matDialog.getView();

        ImageView qualityLogo = dialogView.findViewById(R.id.dialog_download_iv_quality);
        TextView fileSize = dialogView.findViewById(R.id.dialog_download_tv_size);

        switch (quality) {
            case Q_3D:
                qualityLogo.setImageDrawable(quality3Drawable);
                fileSize.setText(presenter.getMovieSize(Quality.Q_3D));
                break;
            case Q_720P:
                qualityLogo.setImageDrawable(quality720pDrawable);
                fileSize.setText(presenter.getMovieSize(Quality.Q_720P));
                break;
            case Q_1080P:
                qualityLogo.setImageDrawable(quality1080pDrawable);
                fileSize.setText(presenter.getMovieSize(Quality.Q_1080P));
                break;
        }
        matDialog.show();
    }

    private void setupInfoDialogButtons(MaterialDialog.Builder builder, Quality quality) {
        builder.positiveText(dialogDownload);
        builder.onPositive((dialog, which) -> requestPermission(quality));

        builder.neutralText(dialogCopyMagnet);
        builder.onNeutral((dialog, which) -> presenter.copyMagnet(builder.getContext(), quality));

        builder.negativeText(dialogDismiss);
        builder.onNegative((dialog, which) -> dialog.dismiss());
    }

    private void requestPermission(Quality quality) {
        RxPermissions rxPermissions = new RxPermissions(getContext());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> presenter.onPermGranted(getContext(), quality, granted), Timber::e);
    }
}
