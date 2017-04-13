package com.castrodev.wishlist.detail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.main.MainActivity;
import com.castrodev.wishlist.utils.DateUtils;
import com.castrodev.wishlist.view.DatePickerFragment;
import com.castrodev.wishlist.view.PriorityPickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements DetailView, View.OnClickListener
        , DatePickerDialog.OnDateSetListener, PriorityPickerFragment.OnPrioritySelectedListener {

    public static final String FORMAT = "dd/MM/yyyy";
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

    @OnClick({R.id.til_when, R.id.iv_when})
    public void onWhenClicked(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick({R.id.til_why, R.id.iv_why})
    public void onWhyClicked(View v) {
        DialogFragment priorityPickerFragment = new PriorityPickerFragment();
        priorityPickerFragment.show(getSupportFragmentManager(), "priorityPicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = DateUtils.getDateSelectedWithFormat(year, month, dayOfMonth, FORMAT);
        tilWhen.getEditText().setText(selectedDate);
    }

    @Override
    public void onPrioritySelected(String priority) {
        tilWhy.getEditText().setText(priority);
    }

    private void clearErrors() {
        tilWhat.setErrorEnabled(false);
        tilWhen.setErrorEnabled(false);
        tilWhy.setErrorEnabled(false);
        tilWhere.setErrorEnabled(false);
        tilHowMuch.setErrorEnabled(false);

    }
}
