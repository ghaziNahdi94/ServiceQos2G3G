package com.example.ghazi.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by ghazi on 21/07/2016.
 */
public class DownloadFileFTP extends AsyncTask<String,Long,String> {

    Context context = null;

    private Handler  cnxEtablieDown = null;
    private Handler cnxEchoueDown = null;
    private Handler progressShowDown = null;
    private Handler progressDown = null;
    private Handler downReuissit = null;
    private Handler downEchoue = null;


    private File file = null;

    private Date dateDebut = null;
    private Date dateFin = null;
    private long duree = 0;
    private int avance = 0;
    private int taille = 0;
    private boolean showen = false;
    private boolean  statut;


    public DownloadFileFTP(Context c,Handler cnxEtablieDown, Handler cnxEchoueDown, Handler progressShowDown, Handler progressDown, Handler downReuissit, Handler downEchoue) {
        this.cnxEtablieDown = cnxEtablieDown;
        this.cnxEchoueDown = cnxEchoueDown;
        this.progressShowDown = progressShowDown;
        this.progressDown = progressDown;
        this.downReuissit = downReuissit;
        this.downEchoue = downEchoue;

        context = c;
    }

    //Params :
    // 0 -> File to create
    // 1 -> ADDRESS
    // 2 -> USER
    // 3 -> PASSWORD
    // 4 -> PATH of file
    @Override
    protected String doInBackground(String... params) {


        //connect to server
        FTPClient ftpClient = new FTPClient();
        statut = false;

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
            cnxEtablieDown.sendEmptyMessage(0);
        else
            cnxEchoueDown.sendEmptyMessage(0);


        if(statut == true) {

            statut = false;

            //Téléchargement du fichier

            FileOutputStream fileOutputStream = null;



            try {

               ftpClient.setBufferSize(1024000);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
               ftpClient.enterLocalPassiveMode();




                dateDebut = new Date();



             fileOutputStream = new FileOutputStream("/sdcard/"+params[4].substring(params[4].lastIndexOf("/")+1));


                FTPFile ftpFile = ftpClient.mlistFile(params[4]);
                this.taille = (int) ftpFile.getSize();



                Util.copyStream(ftpClient.retrieveFileStream(params[4]),fileOutputStream,ftpClient.getBufferSize(),
                        CopyStreamEvent.UNKNOWN_STREAM_SIZE,new CopyStreamAdapter(){
                            @Override
                            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                                if(totalBytesTransferred == taille)
                                    statut = true;

                                publishProgress(totalBytesTransferred);
                            }
                        });
                fileOutputStream.close();


                  if (statut != true)
                   downEchoue.sendEmptyMessage(0);


            } catch (IOException e) {
                e.printStackTrace();

                downEchoue.sendEmptyMessage(0);
                Log.e("erreur","aaaaaaaaaaaaaaaaa");



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

                downReuissit.sendEmptyMessage(0);

            }



        }
        return "";
    }



    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);

        if(!showen) {
            progressShowDown.sendEmptyMessage(0);
            showen = true;
        }
        avance =  values[0].intValue();
        progressDown.sendEmptyMessage(0);
    }


    public int getAvance() {
        return avance;
    }

    public long getDuree() {
        return duree;
    }

    public int getTaille() {
        return taille;
    }
}
