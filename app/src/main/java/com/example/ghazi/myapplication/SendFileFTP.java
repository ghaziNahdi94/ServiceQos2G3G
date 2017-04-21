package com.example.ghazi.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.LongSparseArray;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.sql.Time;
import java.util.Date;

/**
 * Created by ghazi on 18/07/2016.
 */
public class SendFileFTP extends AsyncTask<String,Long,String>{


    //private final String ADDRESS = "31.170.160.87";
    //private final String USER = "a8213681";
    //private final String PASSWORD = "ca1920";

    private int avance = 0;

    private Handler cnxEtablie = null;
    private Handler cnxEchoue = null;
    private Handler progressShow = null;
    private Handler progress = null;
    private Handler envReuissit = null;
    private Handler envEchoue = null;





        private File file = null;
        private  Date dateDebut = null;
        private    Date dateFin = null;
    private long duree = 0;

    public SendFileFTP(Handler cnxEtablie, Handler cnxEchoue, Handler progressShow, Handler progress, Handler envReuissit, Handler envEchoue) {
        this.cnxEtablie = cnxEtablie;
        this.cnxEchoue = cnxEchoue;
        this.progressShow = progressShow;
        this.progress = progress;
        this.envReuissit = envReuissit;
        this.envEchoue = envEchoue;
    }


    // 0 -> File for send
    // 1 -> ADDRESS
    // 2 -> USER
    // 3 -> PASSWORD

    @Override
    protected String doInBackground(String... params) {

      file = new File(params[0]);


      //connect to server
        FTPClient ftpClient = new FTPClient();
        boolean statut = false;

        try {


            ftpClient.connect(params[1],21);

            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){

              statut  =  ftpClient.login(params[2],params[3]);






            }




        } catch (IOException e) {
            e.printStackTrace();
            Log.e("erreur","Erreur de connexion !");
        }


        if(statut == true)
            cnxEtablie.sendEmptyMessage(0);
        else
           cnxEchoue.sendEmptyMessage(0);

        if(statut == true) {

            statut = false;

            //Envoie de fichier

            try {


                ftpClient.setBufferSize(1024000);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


                ftpClient.enterLocalPassiveMode();


                CopyStreamListener copyStreamListener = new CopyStreamAdapter() {
                    @Override
                    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

                        publishProgress(totalBytesTransferred);
                    }
                };

                dateDebut = new Date();
                progressShow.sendEmptyMessage(0);
                ftpClient.setCopyStreamListener(copyStreamListener);


                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                InputStream inputStream = bis;




                statut = ftpClient.storeFile(file.getName(), inputStream);







                if (statut != true)
                  envEchoue.sendEmptyMessage(0);



            } catch (IOException e) {
                e.printStackTrace();
                envEchoue.sendEmptyMessage(0);
                Log.e("erreur", "Erreur d'envoie du fichier");
            }



//fermer la cnx


            try {
                ftpClient.logout();
                dateFin = new Date();
                ftpClient.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            }




if(statut == true) {
    duree = (dateFin.getTime() - dateDebut.getTime()) / 1000;
    if (duree == 0)
        duree++;
    envReuissit.sendEmptyMessage(0);

}




        }








        return "";
    }


    @Override
    protected void onProgressUpdate(Long... values) {

        avance =  values[0].intValue();
        progress.sendEmptyMessage(0);

    }





    public int getAvance(){
        return this.avance;
    }

    public long getDuree(){

        return this.duree;
    }

}
