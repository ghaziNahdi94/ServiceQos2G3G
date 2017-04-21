package com.example.ghazi.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ghazi on 11/09/2016.
 */
public class TempsReelActivity extends ActionBarActivity {

    private LinearLayout quatre = null;
    private TextView technologie = null;
    private TextView deux1 = null;
    private TextView deux2 = null;
    private TextView troix1 = null;
    private TextView troix2 = null;
    private TextView quatre1 = null;
    private TextView quatre2 = null;
    private TextView longitude = null;
    private TextView latitude = null;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temps_reel_activity);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        quatre = (LinearLayout) findViewById(R.id.quatreTR);
        technologie = (TextView) findViewById(R.id.un2);
        deux1 = (TextView) findViewById(R.id.deux1);
        deux2 = (TextView) findViewById(R.id.deux2);
        troix1 = (TextView) findViewById(R.id.troix1);
        troix2 = (TextView) findViewById(R.id.troix2);
        quatre1 = (TextView) findViewById(R.id.quatre1);
        quatre2 = (TextView) findViewById(R.id.quatre2);
        longitude = (TextView) findViewById(R.id.cinq2);
        latitude = (TextView) findViewById(R.id.six2);


        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        PhoneStateListener listenerSignal = new PhoneStateListener() {

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);


                int signalValue = -1; //signal value !!


                String networktype = getNetworkType();

                if (signalStrength.isGsm()) {


                    if (signalStrength.isGsm()) {


                        signalValue = (2*signalStrength.getGsmSignalStrength()) -113;

                        if (networktype.equals("2g")) {


                            quatre.setVisibility(View.INVISIBLE);


                            technologie.setText("2G");
                            deux1.setText("Rx Lev : ");
                            deux2.setText(signalValue + " dBm");
                            troix1.setText("Rx Qual : ");
                            troix2.setText(signalStrength.getGsmBitErrorRate() + "");


                        } else {

                            quatre.setVisibility(View.VISIBLE);

                            int ecio = signalStrength.getCdmaEcio();
                            int rssi = signalStrength.getCdmaDbm();

                            technologie.setText("3G");
                            deux1.setText("RSCP : ");
                            deux2.setText((int) (rssi + ecio/10) + " dBm");
                            troix1.setText("RSSI : ");
                            troix2.setText(rssi + " dBm");
                            quatre1.setText("Ec/Io : ");
                            quatre2.setText(ecio/10 + " dB");


                        }


                    }


                }


            }
        };

        manager.listen(listenerSignal, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude.setText(location.getLatitude() + "");
                longitude.setText(location.getLongitude() + "");
                Log.e("changed","changed");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
Log.e("error","error Location");
            return;
        }





        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000,0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,0, locationListener);
            Log.e("prov","provider");

        }else{

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000,0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,0, locationListener);
            Log.e("avaible","not avaible");

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if(keyCode == event.KEYCODE_BACK){
            this.finish();
            return true;
        }else{

            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home :  this.finish();
                return true;

            default:return super.onOptionsItemSelected(item);

        }

    }




    private String getNetworkType(){

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();


        switch (networkType){

            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2g";

            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //http://goo.gl/bhtVT
                return "3g";


            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://goo.gl/9t7yrR
                return "4g";
            default:
                return "Not found";

        }


    }


}
