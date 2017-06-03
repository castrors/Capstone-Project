package com.castrodev.wishlist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.detail.DetailActivity;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.viewholder.WishViewHolder;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    public static final String WISH_OBJECT = "WISH_OBJECT";
    public static final String WISH_KEY = "WISH_KEY";
    public static final int LOGIN_POSITION = 0;
    public static final int LOGOUT_POSITION = 1;
    @BindView(R.id.rv_wishes_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public static final int RC_SIGN_IN = 1;
    private static final String TAG = MainActivity.class.getName();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private MainPresenterImpl presenter;
    private FirebaseRecyclerAdapter<Wish, WishViewHolder> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        setupView();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    TextView username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_username);
                    if (!user.isAnonymous()) {
                        username.setText(user.getDisplayName());
                        navigationView.getMenu().findItem(R.id.drawer_login).setVisible(false);
                        navigationView.getMenu().findItem(R.id.drawer_logout).setVisible(true);
                    } else {
                        username.setText(getString(R.string.default_username));
                        navigationView.getMenu().findItem(R.id.drawer_login).setVisible(true);
                        navigationView.getMenu().findItem(R.id.drawer_logout).setVisible(false);
                    }
                }
            }
        };

        signInAnonymously();

    }

    private void signInAnonymously() {
        if (mFirebaseAuth.getCurrentUser() == null) {
            mFirebaseAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInAnonymously", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    private void setupView() {
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_login:
                                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                login();
                                break;
                            case R.id.drawer_logout:
                                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                                AuthUI.getInstance().signOut(MainActivity.this);
                                signInAnonymously();
                                presenter.onResume();
                                break;
                        }
                        return true;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
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
    public void setItems(Query wishesQuery) {
//        errorView.setVisibility(View.GONE);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        mAdapter = new MainAdapter(presenter, wishesQuery);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void goToDetailActivity(Wish wish, DatabaseReference databaseReference) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(WISH_OBJECT, wish);
        intent.putExtra(WISH_KEY, databaseReference.getKey());
        startActivity(intent);
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
