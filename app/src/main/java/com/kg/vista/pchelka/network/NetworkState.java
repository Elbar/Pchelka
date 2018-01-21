package com.kg.vista.pchelka.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.kg.vista.pchelka.manager.AlertDialogManager;

/**
 * Created by fen on 12.05.2017.
 */

public class NetworkState {
    AlertDialogManager alert = new AlertDialogManager();
    private Context _context;


    public NetworkState(Context context) {
        this._context = context;
    }

    public boolean checkInternetConnection() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity.getActiveNetworkInfo() != null
                && connectivity.getActiveNetworkInfo().isAvailable()
                && connectivity.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            alert.showAlertDialog(_context, "Ошибка", "Отсуствует соединение с интернетом", false);
            alert.dismissProgressDialog();
            return false;
        }
    }


}
