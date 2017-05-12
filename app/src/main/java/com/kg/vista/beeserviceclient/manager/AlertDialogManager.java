package com.kg.vista.beeserviceclient.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class AlertDialogManager {
    AlertDialog alertDialog;

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);


        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
    public void dismissProgressDialog() {
        alertDialog.dismiss();

    }
}