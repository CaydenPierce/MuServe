package com.example.android.bluetoothlegatt;

import android.os.AsyncTask;
import android.util.Log;

public class Saver extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object... params) { //Object is an an array with the socket at the first index and the byte array as the second index
        Log.d("UUID", "Streaming: socket and CSV.");
        return null;
    }
}

