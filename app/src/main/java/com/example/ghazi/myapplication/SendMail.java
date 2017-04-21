package com.example.ghazi.myapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;

/**
 * Created by ghazi on 27/07/2016.
 */
public class SendMail extends IntentService {



    public SendMail() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        String path = intent.getStringExtra("alarmCall");


        Setting setting = new Setting(this);


        int choix = setting.getChoiceEnvoie();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        String type = "WIFI";

        if(networkInfo != null)
         type = networkInfo.getTypeName();




        switch (choix){

            case 1 :

                new Email(setting,path);

                break;

            case 2 :

                if(type.equals("WIFI"))
                    new Email(setting,path);


                break;


            case 3 :
                if(type.equals("mobile"))
                    new Email(setting,path);

                break;


            default:
                new Email(setting,path);
                Log.e("erreur","erreur default SendEmail");


        }








    }




}
