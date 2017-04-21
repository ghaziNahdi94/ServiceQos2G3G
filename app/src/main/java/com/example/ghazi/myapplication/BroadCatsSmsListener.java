package com.example.ghazi.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Created by ghazi on 15/07/2016.
 */
public class BroadCatsSmsListener extends BroadcastReceiver{

    private Context context = null;
    private boolean  isRegistered;


    public BroadCatsSmsListener() {
    }




    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;


        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {


            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {

                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    final String messageBody = messages[0].getMessageBody();
                    final String phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Date date = new Date();
                    final String chDate = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + " à " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

                    final String result = "SMS réçue de : " + phoneNumber + "\n" + "Date de réçue : " + chDate + "\n" + "Message : " + messageBody+"\n";

                    saveTraceInFile(result);


                }


            }
        }}






    private void saveTraceInFile(String result){

        Setting setting = new Setting(context);
        Info info = new Info(context);

        if(setting.getApplicationActive()){

        final String PATH = context.getFilesDir().getAbsolutePath();
        final String PATH_SMS = PATH+"/sms.txt";
        final String separateur = "--------------------\n";
        final byte[] separateurBytes = separateur.getBytes();
        final String finSep = "********************\n";
        final byte[] finSepBytes = finSep.getBytes();

        File file = new File(PATH_SMS);


        //verifier que la trace est nouvelle
        boolean existe = false;
        try {
            FileInputStream fis = new FileInputStream(PATH_SMS);

            byte[] texteBytes = new byte[(int) file.length()];
            fis.read(texteBytes);

            String texte = new String(texteBytes);

            if(!texte.equals("")) {

                String[] parts = texte.split("--------------------");


                for (int i = 1; i < parts.length; i++) {


                    String[] lignes = parts[i].split("\n");


                    if (lignes[2].equals(result.split("\n")[1])) {

                        existe = true;
                        break;

                    }


                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




//save if not exist

        if(!existe){


            //Info.txt


            info.setNbrSMS(info.getNbrSMS()+1);




            try {

                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath(),true);
                fos.write(separateurBytes);

                fos.write(result.getBytes());

                fos.write(finSepBytes);

                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }}}








}