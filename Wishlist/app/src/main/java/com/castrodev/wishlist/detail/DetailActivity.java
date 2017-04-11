package com.castrodev.wishlist.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView, View.OnClickListener {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.til_what)
    TextInputLayout tilWhat;
    @BindView(R.id.til_when)
    TextInputLayout tilWhen;
    @BindView(R.id.til_why)
    TextInputLayout tilWhy;
    @BindView(R.id.til_where)
    TextInputLayout tilWhere;
    @BindView(R.id.til_how_much)
    TextInputLayout tilHowMuch;
    @BindView(R.id.til_observation)
    TextInputLayout tilObservation;
    @BindView(R.id.fab_check)
    FloatingActionButton fabCheck;

    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        fabCheck.setOnClickListener(this);
        presenter = new DetailPresenterImpl(this);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setWhatError() {
        tilWhat.setError(getString(R.string.what_error));
    }

    @Override
    public void setWhenError() {
        tilWhen.setError(getString(R.string.when_error));
    }

    @Override
    public void setWhyError() {
        tilWhy.setError(getString(R.string.why_error));
    }

    @Override
    public void setWhereError() {
        tilWhere.setError(getString(R.string.where_error));
    }

    @Override
    public void setHowMuchError() {
        tilHowMuch.setError(getString(R.string.how_much_error));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        clearErrors();
        presenter.validateData(
                tilWhat.getEditText().getText().toString()
                , tilWhen.getEditText().getText().toString()
                , tilWhy.getEditText().getText().toString()
                , tilWhere.getEditText().getText().toString()
                , tilHowMuch.getEditText().getText().toString());
    }

    private void clearErrors() {
        tilWhat.setErrorEnabled(false);
        tilWhen.setErrorEnabled(false);
        tilWhy.setErrorEnabled(false);
        tilWhere.setErrorEnabled(false);
        tilHowMuch.setErrorEnabled(false);

    }
}
