package com.example.david.dpsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by david on 2016-11-07.
 */
public class PleaseLogin extends DialogFragment {


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Please Log In");

        alertDialog.setMessage("You must be logged in to create post");

        /*final EditText editText = new EditText(getActivity());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(layoutParams);*/
       // alertDialog.setView(editText);

        alertDialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new LogIn()).commit();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setShowsDialog(false);
                dismiss();
            }
        });
        return alertDialog.create();
    }


}
