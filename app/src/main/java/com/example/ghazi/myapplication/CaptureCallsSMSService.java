package com.example.ghazi.myapplication;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by ghazi on 27/07/2016.
 */
public class CaptureCallsSMSService extends Service {

    private boolean captureTrace = true;
    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";
    Intent intent = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        this.intent = intent;
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        if (intent != null)
            captureTrace = intent.getBooleanExtra("capture", true);


        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ACTION_IN);
        intentFilter.addAction(ACTION_OUT);
        final BroadCatPhoneCall myBroadCats = new BroadCatPhoneCall(ACTION_IN, ACTION_OUT);

        if (captureTrace) {
            this.registerReceiver(myBroadCats, intentFilter);
        } else {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_NONE);

        }
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_SENT");
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        BroadCatsSmsListener broadCatsSmsListener = new BroadCatsSmsListener();
        if (captureTrace) {
            this.registerReceiver(broadCatsSmsListener, intentFilter);

        } else {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_NONE);


        }

        if (captureTrace) {

            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);



            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                   myBroadCats.setLongitude(location.getLongitude());
                    myBroadCats.setLatitude(location.getLatitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                    Log.e("stat","statut : "+status);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.e("prov","provider enable");
                }

                @Override
                public void onProviderDisabled(String provider) {



                }
            };

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("erreur gps","error gps");
                return;
            }


                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, listener);
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);




            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SignalListener signalListener = new SignalListener(CaptureCallsSMSService.this,myBroadCats);
            telephonyManager.listen(signalListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }



        final Uri SMS_URI = Uri.parse("content://sms");
        SmsObserver smsObserver = new SmsObserver(new Handler(),this);


        if(captureTrace) {
            getContentResolver().registerContentObserver(SMS_URI, true, smsObserver);
        } else {
            getContentResolver().unregisterContentObserver(smsObserver);
        }

    }
}
