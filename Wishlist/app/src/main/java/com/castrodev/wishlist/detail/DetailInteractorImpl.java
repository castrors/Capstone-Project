package com.castrodev.wishlist.detail;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.WishUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailInteractorImpl implements DetailInteractor {


    @Override
    public void validate(Wish wish, OnSaveFinishedListener listener) {


        if (WishUtils.isEmpty(wish.getName())) {
            listener.onWhatError();
            return;
        }
        if (!WishUtils.isValidDate(wish.getDueDate())) {
            listener.onWhenError();
            return;
        }

        if (!WishUtils.isPriorityValid(wish.getPriority())) {
            listener.onWhyError();
            return;
        }

        if (!WishUtils.isLocationValid(wish.getLocation())) {
            listener.onWhereError();
            return;
        }

        if (!WishUtils.isValueValid(wish.getValue())) {
            listener.onHowMuchError();
            return;
        }

        listener.onValidationSuccess(wish);
    }

    @Override
    public void uploadPhoto(Context context, Uri photoUri, final OnUploadFinishedListener uploadListener) {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference wishPhotos = firebaseStorage.getReference().child("wish_photos");

        StorageReference photoRef = wishPhotos.child(photoUri.getLastPathSegment());
        photoRef.putFile(photoUri)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        uploadListener.onUploadSuccess(downloadUrl.toString());
                    }
                })
        .addOnFailureListener((Activity) context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadListener.onUploadError();
            }
        });
    }

    @Override
    public void save(String wishKey, Wish wish) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference wishesDatabaseReference = firebaseDatabase.getReference().child(user.getUid()).child("wishes");

            if (wishKey != null && wishKey != "") {
                wishesDatabaseReference.child(wishKey).setValue(wish);
            } else {
                wishesDatabaseReference.push().setValue(wish);
            }
        }
    }

    @Override
    public void delete(String wishKey) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference wishesDatabaseReference = firebaseDatabase.getReference().child(user.getUid()).child("wishes");

            if (wishKey != null && wishKey != "") {
                wishesDatabaseReference.child(wishKey).removeValue();
            }
        }
    }
}