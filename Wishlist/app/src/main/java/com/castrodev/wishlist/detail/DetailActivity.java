package com.castrodev.wishlist.detail;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.main.MainActivity;
import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.DateUtils;
import com.castrodev.wishlist.view.DatePickerFragment;
import com.castrodev.wishlist.view.PriorityPickerFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.castrodev.wishlist.main.MainActivity.WISH_OBJECT;

public class DetailActivity extends AppCompatActivity implements DetailView, View.OnClickListener
        , DatePickerDialog.OnDateSetListener, PriorityPickerFragment.OnPrioritySelectedListener, GoogleApiClient.OnConnectionFailedListener {


    public static final String FORMAT = "dd/MM/yyyy";
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PHOTO_PICKER_REQUEST = 2;

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
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    private DetailPresenter presenter;
    private Location locationSelected;
    private Uri photoUri;
    private String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getIntentData();
        fabCheck.setOnClickListener(this);
        presenter = new DetailPresenterImpl(this);

        GoogleApiClient googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }

    private void getIntentData() {
        if(getIntent().hasExtra(WISH_OBJECT)){
            Wish wish = getIntent().getParcelableExtra(WISH_OBJECT);
            setDataToView(wish);
        }
    }

    private void setDataToView(Wish wish) {
        tilWhat.getEditText().setText(wish.getName());
        tilWhen.getEditText().setText(DateUtils.getDateWithFormat(wish.getDueDate(), FORMAT));
        //TODO Continuar
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
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public void setUploadError() {
        Toast.makeText(this, "Upload Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        clearErrors();
        presenter.validateData(
                tilWhat.getEditText().getText().toString()
                , tilWhen.getEditText().getText().toString()
                , tilWhy.getEditText().getText().toString()
                , locationSelected
                , tilHowMuch.getEditText().getText().toString()
                , tilObservation.getEditText().getText().toString()
                , photoUrl
                , photoUri.toString());

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

    @OnClick({R.id.til_where, R.id.iv_where})
    public void onWhereClicked(View v) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_photo)
    public void onPhotoClicked(View v) {
        if (!isStoragePermissionGranted(this)) {
            requestStoragePermission(this);
        } else {
            pickPhoto();
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKER_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickPhoto();
            } else {
                Log.e("", "Erro permissão");
            }
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                    locationSelected = new Location((String) place.getName(), place.getLatLng().latitude, place.getLatLng().longitude);
                    tilWhere.getEditText().setText(locationSelected.getName());
                    break;
                case PHOTO_PICKER_REQUEST:
                    photoUri = data.getData();
                    Picasso.with(this)
                            .load(photoUri)
                            .error(R.drawable.ic_photo_camera)
                            .into(ivPhoto);

                    presenter.uploadPhoto(this, photoUri);
                    break;
            }
        }
    }

    private void clearErrors() {
        tilWhat.setErrorEnabled(false);
        tilWhen.setErrorEnabled(false);
        tilWhy.setErrorEnabled(false);
        tilWhere.setErrorEnabled(false);
        tilHowMuch.setErrorEnabled(false);

    }

    public static boolean isStoragePermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

}
