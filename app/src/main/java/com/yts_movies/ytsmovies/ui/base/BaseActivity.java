package com.yts_movies.ytsmovies.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


@SuppressLint("Registered")
//SuppressLint will tell you whenever something in your code isn't optimal or may crash
public abstract class BaseActivity extends AppCompatActivity {

    // vars
    private Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    } // on create function

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initPresenter();
    } // set content view function

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home :
            super.onBackPressed();
            break;
        }
        return super.onOptionsItemSelected(item);
    } // get back when select item

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    } // attatch font

    protected Activity getContext() {
        return context;
    } // getter method activity

    protected abstract void initPresenter();
}
