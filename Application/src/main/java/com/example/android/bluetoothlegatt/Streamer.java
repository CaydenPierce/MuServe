package com.example.android.bluetoothlegatt;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;

public class Streamer extends AsyncTask<Object, Void, Void> {
    int port = 9999;
    //final String ip = "192.168.50.132"; //luke
    String ip = "192.168.50.172"; //cayden desktop

    Streamer(String ipString,int portNum){
        super();
        port=portNum;
        ip=ipString;
    }

    @Override
    protected Void doInBackground(Object... params) { //Object is an an array with the socket at the first index and the byte array as the second index
        Log.d("UUID", "Sending packet now.");
        try {
            InetAddress serverAddr = InetAddress.getByName(ip);
            //byte[] buf = ((String) params[1]).getBytes();
            byte[] buf = (byte[]) params[1];
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, port);
            ((DatagramSocket) params[0]).send(packet);
        } catch (SocketException e) {
            Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
            Log.e("Udp Send:", "IO Error:", e);
        }

        return null;
    }

}
