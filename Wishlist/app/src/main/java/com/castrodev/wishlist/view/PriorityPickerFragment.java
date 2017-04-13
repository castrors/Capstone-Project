package com.castrodev.wishlist.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.castrodev.wishlist.R;

/**
 * Created by rodrigocastro on 13/04/17.
 */

public class PriorityPickerFragment extends DialogFragment
        implements DialogInterface.OnClickListener {


    public interface OnPrioritySelectedListener {
        void onPrioritySelected(String priority);
    }

    private OnPrioritySelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnPrioritySelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPrioritySelectedListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_priority)
                .setItems(R.array.priority_array, this);
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int selected) {
        if (listener != null) {
            Resources res = getResources();
            String[] priority = res.getStringArray(R.array.priority_array);
            listener.onPrioritySelected(priority[selected]);
        }
    }
}
