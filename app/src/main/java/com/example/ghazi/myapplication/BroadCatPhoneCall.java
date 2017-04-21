package com.example.ghazi.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by ghazi on 14/07/2016.
 */
public class BroadCatPhoneCall extends BroadcastReceiver {

    private Context context = null;


    private Bundle bundle = null;
    private final String ACTION_IN;
    private final String ACTION_OUT;

    private String lastState = "";
    private String lastNumber = "";


    private boolean out = false;


    private String technology = null;
    private int signalValue = 0;
    private int rscp = 0;
    private int rssi = 0;
    private int gsmEcio = 0;
    private int rxQual = 0;
    private int rxLev = 0;
    public double longitude = 0;
    public double latitude = 0;


    private String resultCall = "";


    private Date debut = null;
    private Date fin = null;
    private Date date = null;


    private int call = 0;




    public BroadCatPhoneCall() {

        this.ACTION_IN = "";
        this.ACTION_OUT = "";


    }

    public BroadCatPhoneCall(String actionIn, String actionOut) {

        this.ACTION_IN = actionIn;
        this.ACTION_OUT = actionOut;

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


        if (intent.getAction().equals(ACTION_IN)) {


            if ((bundle = intent.getExtras()) != null) {


                String state = bundle.getString(TelephonyManager.EXTRA_STATE);


                //le Tel Sonne


                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                    resultCall = "";
                    lastNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    date = new Date();
                    String d = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
                    String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

                    resultCall += "Drop call le " + d + " à " + time + "\n";
                    resultCall += "Numéro : " + lastNumber + "\n";


                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {


                    if (!out) {
                        debut = new Date();

                        lastState = TelephonyManager.EXTRA_STATE_OFFHOOK;

                    } else {

                        debut = new Date();
                        String d = debut.getDate() + "/" + (debut.getMonth() + 1) + "/" + (debut.getYear() + 1900);
                        String time = debut.getHours() + ":" + debut.getMinutes() + ":" + debut.getSeconds();


                        resultCall += "Drop call le " + d + " à " + time + "\n";
                        resultCall += "Numéro : " + lastNumber + "\n";
                        lastState = TelephonyManager.EXTRA_STATE_OFFHOOK;

                    }






                } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {


                    Info info = new Info(context);
                    int appelInterup = info.getNbrAppelInterrompu();

                    if(!verifDropCall()) {
                        resultCall = "Not"+ resultCall;

                    }else {
                        info.setNbrAppelInterrompu(appelInterup + 1);
                    }

                    fin = new Date();


                    if(technology.equals("2g")) {
                        resultCall += "Technologie : 2G\n";
                        resultCall += "Rx Lev : " + rxLev + " dBm\n";
                        resultCall += "Rx Qual : "+rxQual+ " %\n";
                        if(!(longitude == 0 && latitude == 0)) {
                            resultCall += "Longitude : " + longitude + "\n";
                            resultCall += "Latitude : " + latitude + "\n";
                        }else{
                            resultCall += "Longitude : N/A\n";
                            resultCall += "Latitude : N/A\n";

                        }

                    }else if(technology.equals("3g")){

                        resultCall += "Technologie : 3G\n";
                        resultCall += "RSSI : "+rssi+ " dBm\n";
                        resultCall += "RSCP : "+rscp+ " dBm\n";
                        resultCall += "Ec/Io : "+gsmEcio+ " dB\n";
                        if(!(longitude == 0 && latitude == 0)) {
                            resultCall += "Longitude : " + longitude + "\n";
                            resultCall += "Latitude : " + latitude + "\n";
                        }else{
                            resultCall += "Longitude : N/A\n";
                            resultCall += "Latitude : N/A\n";

                        }
                    }



                    saveTraceInFile(resultCall);



                }







            }



        } else if (intent.getAction().equals(ACTION_OUT)) {

            out = true;
            if ((bundle = intent.getExtras()) != null) {
                resultCall = "";
                lastNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            }

        }//




    }


//methode


    private boolean verifDropCall() {


        if (technology.equals("2g")) {

            if (rxLev <= -105) {  // || rxLev < -95 && rxQual > 4
                Log.e("rxLev",rxLev+"");
                return true;
            }else {
                return false;
            }

        } else {


            if (signalValue < -115 || gsmEcio < -16 || rssi <= -125 || rssi == 0) {
            return true;

            }else{
                return false;
            }
        }


    }




    private void saveTraceInFile(String result) {

        Setting setting = new Setting(context);
        Info info = new Info(context);



        if (setting.getApplicationActive()) {


            final String PATH = context.getFilesDir().getAbsolutePath();
            final String PATH_CALLS = PATH + "/calls.txt";
            final String separateur = "--------------------\n";
            final byte[] separateurBytes = separateur.getBytes();
            final String finSep = "********************\n";
            final byte[] finSepBytes = finSep.getBytes();

            File callsFile = new File(PATH_CALLS);

            //verifier que la trace est nouvelle
            boolean existe = false;
            try {
                FileInputStream fis = new FileInputStream(PATH_CALLS);

                byte[] texteBytes = new byte[(int) callsFile.length()];
                fis.read(texteBytes);

                String texte = new String(texteBytes);

                if (!texte.equals("")) {

                    String[] parts = texte.split("--------------------");



                    for (int i = 1; i < parts.length; i++) {

                        String[] lignes = parts[i].split("\n");


                        if (lignes[1].split("le ")[1].equals(result.split("\n")[0].split("le ")[1])) {

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
            if (!existe){

                //Info.txt
                call = info.getNbrAppel();

            int appelInterup = info.getNbrAppelInterrompu();

                info.setNbrAppel(call+1);



                if(call != 0)
                info.setTauxAppelInterrompu((long) (appelInterup/call)*100);




                 try {

                    FileOutputStream fos = new FileOutputStream(PATH_CALLS, true);
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

        }}
    }







    public boolean isStateHook(){

    if(lastState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        return true;
        else
        return false;

    }





    //setters


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSignalValue(List<Integer> signalValues, String technology,int signalValue) {


        this.technology = technology;

        this.signalValue = signalValue;

        if (technology.equals("2g")) {

            rxQual = signalValues.get(0);  // 0 : Rx Qual
            rxLev = signalValues.get(1); // 1 : Rx Lev
            rssi = -1;
            gsmEcio = -1;

        } else if (technology.equals("3g")) {

            rscp = signalValues.get(0);// 0 : RSCP
            rssi = signalValues.get(1); // 1 : RSSI
            gsmEcio = signalValues.get(2); // 2 : ECIO

            rxLev = -1;
            rxQual = -1;
        }


    }



}
