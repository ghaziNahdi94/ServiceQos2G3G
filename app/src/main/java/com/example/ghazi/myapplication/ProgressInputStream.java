package com.example.ghazi.myapplication;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ghazi on 18/07/2016.
 */
public class ProgressInputStream extends InputStream{

private InputStream inputStream = null;

    private int TEN_KILOBYTES = 1024*10;
    private long progress;
    private long lastUpdate;
    private boolean closed ;


    public ProgressInputStream(InputStream inputStream) {
        this.inputStream = inputStream;

        this.progress = 0;
        this.lastUpdate = 0;
        this.closed = false;
    }

    @Override
    public int read() throws IOException {
        int count = inputStream.read();

        if(count == -1)
            Log.e("prog",progress+"");

        return incrementCounterAndUpdateDisplay(count);
    }



    @Override
    public void close() throws IOException {
        super.close();
        if (closed)
            throw new IOException("already closed");
        closed = true;
    }




    private int incrementCounterAndUpdateDisplay(int count) {

            progress += count;
       // lastUpdate = maybeUpdateDisplay(progress, lastUpdate);
        return count;
    }

    private long maybeUpdateDisplay(long progress, long lastUpdate) {

        //    if (progress - lastUpdate > TEN_KILOBYTES) {
               // lastUpdate = progress;
                //  sendLong(PROGRESS_UPDATE, progress); Send value here


          //  }
            return lastUpdate;

    }




}
