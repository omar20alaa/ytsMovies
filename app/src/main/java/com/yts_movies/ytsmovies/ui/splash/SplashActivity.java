package com.yts_movies.ytsmovies.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yts_movies.ytsmovies.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    // vars
    private SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SplashPresenter().onAttatch(this,null);
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
    this.presenter = presenter ;
    }

    @Override
    public void setActionBar() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

}
