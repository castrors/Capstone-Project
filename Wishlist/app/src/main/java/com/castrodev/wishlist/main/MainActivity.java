package com.castrodev.wishlist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.detail.DetailActivity;
import com.castrodev.wishlist.model.Wish;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.rv_wishes_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static final int RC_SIGN_IN = 1;
    private static final String TAG = MainActivity.class.getName();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
//                     User is signed out
//                    login();
                }
            }
        };


        mFirebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void login() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setIsSmartLockEnabled(false)
                        .setProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    @OnClick(R.id.fab_add)
    void onFabClicked() {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.login:
                login();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<Wish> wishes) {
//        errorView.setVisibility(View.GONE);
//        MainAdapter adapter = (MainAdapter) recyclerView.getAdapter();
//        if (adapter == null) {
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(new MainAdapter(wishes, presenter));
//        } else {
//            adapter.concatenateDataSet(redditObject);
//        }
    }

    @Override
    public void goToDetailActivity(Wish wish) {

    }

    @Override
    public void showDefaultError() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
