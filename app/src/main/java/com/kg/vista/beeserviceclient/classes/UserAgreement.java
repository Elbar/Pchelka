package com.kg.vista.beeserviceclient.classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import com.kg.vista.beeserviceclient.R;


public class UserAgreement {

    private Activity mActivity;

    public UserAgreement(Activity context) {
        mActivity = context;
    }

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    public void show() {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        String title = mActivity.getString(R.string.user_agreement_header);

        String message = mActivity.getString(R.string.user_agreement_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.apply();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();

    }

}