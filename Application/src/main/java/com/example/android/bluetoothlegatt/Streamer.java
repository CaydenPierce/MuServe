package com.example.android.bluetoothlegatt;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.nio.ByteBuffer;

public class Streamer extends AsyncTask<Object, Void, Void> {
    int port = 9999;
    //final String ip = "192.168.50.132"; //luke
    String ip = "192.168.50.172"; //cayden desktop
    int id = 1; //cayden desktop

    private Context mContext;
    Streamer(Context context, String ipString,int portNum, int idInt){
        super();
        mContext = context;
        port=portNum;
        ip=ipString;
        id=idInt;
    }

    @Override
    protected Void doInBackground(Object... params) { //Object is an an array with the socket at the first index and the byte array as the second index
        Log.d("UUID", "Streaming: socket and CSV.");
        sendPacket(params[0], params[1], params[3]);
        saveToCSV(params[2], params[1], params[3]);
        return null;
    }

    private void sendPacket(Object socket, Object data, Object handle){
        try {
            byte [] myHandle = {(byte) ((int) handle)};
            Log.d("yeah", Integer.toString(id));
            byte [] mySession = {(byte) ((int) id)};
            byte[] buf = (byte[]) data;
            InetAddress serverAddr = InetAddress.getByName(ip);

            byte[] toSend = new byte[mySession.length + myHandle.length + buf.length];
            System.arraycopy(mySession, 0, toSend, 0, mySession.length);
            System.arraycopy(myHandle, 0, toSend, mySession.length, myHandle.length);
            System.arraycopy(buf, 0, toSend, myHandle.length+mySession.length, buf.length);

            DatagramPacket packet = new DatagramPacket(toSend, toSend.length,serverAddr, port);
            ((DatagramSocket) socket).send(packet);
        } catch (SocketException e) {
            Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
            Log.e("Udp Send:", "IO Error:", e);
        }
    }

    private void saveToCSV(Object name, Object data, Object handle){
        byte[] buf = (byte[]) data;
        int myHandle = (int) handle;
        String content = procData(buf, myHandle);
        Log.d("content", content);
        try {
            String filename = (String) name;
            Log.d("asdf", mContext.getExternalFilesDir(null) + filename + ".csv");
            File file = new File(mContext.getExternalFilesDir(null) + "/" + filename +".csv");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String procData(byte[] data, int handle){
        String content = "";
        Log.d("YO", Integer.toString(data.length));
        int [] stuff = convert(data);
        content=content.concat(Integer.toString(handle));
        content=content.concat(",");
        content=content.concat(Integer.toString(stuff[0]));
        for (int i=1; i< stuff.length; i++){
            Log.d("ARR", Integer.toString(stuff[i]));
            content=content.concat(",");
            String val = Double.toString((stuff[i]-2048)*0.40293);
            content=content.concat(val);
        }
        content = content.concat("\n");
        return content;
    }

    /*
    bit pattern:
    -16bits x1
    -12bits x12
     */
    public int[] convert(byte buf[]) {
        int intArr[] = new int[13];
        int offset = 0;

        intArr[0] = ((buf[0]&0xff)<<8 )|((buf[1]&0xff));

        for (int i=0; i<18/3;i++){
            intArr[i*2+1] = ((buf[i*3+2]&0xff)<<4 )|((buf[i*3+2+1]&0xf0)>>4);
            intArr[i*2+1+1] =((buf[i*3+2+1]&0x0f)<<8 )|((buf[i*3+2+2]&0xff));
        }

        Log.d("decoded signals", "index i:"+String.valueOf(1)+" , "+String.valueOf(intArr[0]));
        return intArr;

    }

}
